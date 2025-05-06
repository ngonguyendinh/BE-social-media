package com.example.mxh.model.chat;

import com.example.mxh.model.message.MessageDto;
import com.example.mxh.model.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class ChatDto {
    private int id;
    private String chat;
    private String chatImg;
    private List<MessageDto> messages = new ArrayList<>();
    private LocalDateTime timeStamp;
    private List<UserDto> users = new ArrayList<>();
}
