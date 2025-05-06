package com.example.mxh.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormCreateUser {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gioiTinh;
    private String ngaySinh;
}
