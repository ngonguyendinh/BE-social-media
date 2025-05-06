package com.example.mxh.model.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private Set<Integer> follower;
    private Set<Integer> following;
    private String avatar;
    private String backGround;

}
