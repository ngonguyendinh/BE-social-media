package com.example.mxh.model.reels;

import com.example.mxh.model.user.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
public class ReelsDto {
    private Long id;

    private String title;

    private String urlVideo;

    private LocalDateTime createdAt;

    private UserDto user;
}
