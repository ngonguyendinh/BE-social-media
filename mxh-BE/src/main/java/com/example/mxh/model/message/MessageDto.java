package com.example.mxh.model.message;

import com.example.mxh.model.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MessageDto {
    private Long id;
    private String content;
    private String image;
    private UserDto user;
    private LocalDateTime timeStamp;
}
