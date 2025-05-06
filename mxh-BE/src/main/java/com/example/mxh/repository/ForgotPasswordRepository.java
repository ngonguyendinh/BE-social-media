package com.example.mxh.repository;

import com.example.mxh.model.user.ForgotPassword;
import com.example.mxh.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Integer> {
    @Query("select fd from ForgotPassword fd where fd.otp =?1 and fd.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(int otp, User user);
}
