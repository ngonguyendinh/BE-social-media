package com.example.mxh.form;

import lombok.Builder;
import lombok.Data;

@Builder
public record ChangePassword(String email,String password, String confirmPassword) {
}
