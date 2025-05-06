package com.example.mxh.controller;

import com.example.mxh.config.CustomUserDetailService;
import com.example.mxh.config.jwt.JwtProvider;
import com.example.mxh.form.FormCreateUser;
import com.example.mxh.form.LoignRequest;
import com.example.mxh.repository.UserRepository;
import com.example.mxh.response.AuthResponse;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private IUserService userService;
    private UserRepository userRepository;
    private CustomUserDetailService customUserDetailService;
    private PasswordEncoder  passwordEncoder;
    @PostMapping("/register")
    public AuthResponse create(@RequestBody FormCreateUser form) throws Exception {
        var response = userService.create(form);
        var user = userRepository.findByUsername(form.getUsername());
        if (user == null){
            throw new Exception("can't generate Token, please check your username");
        }
        return response;
    }
    //auth/signin
    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody LoignRequest loignRequest){
        Authentication authentication = authenticate(loignRequest.getUsername(),loignRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(token,"login success");
        return response;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        if (userDetails == null){
            throw new BadCredentialsException("invalid username");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("password not matched");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
