package com.example.mxh.controller;

import com.example.mxh.form.ChangePassword;
import com.example.mxh.form.FormForgotPassword;
import com.example.mxh.form.InputOtp;
import com.example.mxh.model.user.ForgotPassword;
import com.example.mxh.model.user.User;
import com.example.mxh.repository.ForgotPasswordRepository;
import com.example.mxh.repository.UserRepository;
import com.example.mxh.service.EmailService;
import com.example.mxh.util.MailBody;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api/forgotPassword")
@AllArgsConstructor
public class ForgotPasswordController {
    private UserRepository userRepository;
    private EmailService emailService;
    private ForgotPasswordRepository forgotPasswordRepository;
    private PasswordEncoder passwordEncoder;
    @PostMapping("/verifyMail")
    public ResponseEntity<String> verifyMail(@RequestBody FormForgotPassword forgotPassword){
        User user = userRepository.findByEmail(forgotPassword.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("kiểm tra lại email của bạn nhập"));
        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(forgotPassword.getEmail()).text("Mã OTP quên mật khẩu của bạn là :"+otp)
                .subject("OTP cho yêu cầu quên mật khẩu").build();
        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+300*1000))
                .user(user)
                .build();
        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);
        return ResponseEntity.ok("Email xác thực");
    }
    @PostMapping("/verify/otp")
    public ResponseEntity<String> verifyOtp(@RequestBody InputOtp otp){
        User user = userRepository.findByEmail(otp.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("kiểm tra lại email của bạn nhập"));
        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp.getOtp(),user)
                .orElseThrow(()-> new RuntimeException("OTP không đúng vui lòng kiểm tra lại trong email của bạn: "+otp.getEmail()));
        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP đã hết hạn ", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP xác thực thành công");
    }
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword form){
        if(!Objects.equals(form.password(),form.confirmPassword())){
            return  new ResponseEntity<>("mật khẩu không khớp. Vui lòng kiểm tra lại",HttpStatus.EXPECTATION_FAILED);
        }
        String endcodePassword = passwordEncoder.encode(form.password());
        User user = userRepository.findByEmail(form.email()).get();
        user.setPassword(endcodePassword);
        userRepository.save(user);
        return ResponseEntity.ok("thay đổi mật khẩu thành công");

    }
    private int otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000,999_999);
    }

}
