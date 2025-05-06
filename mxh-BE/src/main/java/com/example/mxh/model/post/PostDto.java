package com.example.mxh.model.post;

import com.example.mxh.model.comment.CommentDto;
import com.example.mxh.model.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PostDto {
    private int id;
    private String caption;
    private String image;
    private String video;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
    private List<UserDto> liked;
    private List<CommentDto> comments = new ArrayList<>();
}
