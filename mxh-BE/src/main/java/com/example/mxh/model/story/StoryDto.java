package com.example.mxh.model.story;


import com.example.mxh.model.user.UserDto;
import lombok.Data;


import java.time.LocalDateTime;
@Data
public class StoryDto {
    private int id;

    private String caption;

    private String image;

    private UserDto user;

    private LocalDateTime timeStamp;
}
