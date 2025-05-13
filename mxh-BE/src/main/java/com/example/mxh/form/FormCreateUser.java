package com.example.mxh.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
public class FormCreateUser {
    private String username;
    private String password;
    @NotBlank(message = "Email không được để trống")
    private String email;
    private String firstName;
    private String lastName;
    private String gioiTinh;
    private String ngaySinh;
}
