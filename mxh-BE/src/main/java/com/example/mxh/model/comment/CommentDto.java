package com.example.mxh.model.comment;

import com.example.mxh.model.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class CommentDto {

    private  int id;


    private String content;


    private String image;


    private UserDto user;


    private List<UserDto> liked = new ArrayList<>();


    private LocalDateTime createdAt;
}
