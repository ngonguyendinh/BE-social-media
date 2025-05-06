package com.example.mxh.map;

import com.example.mxh.form.FormCreateUser;
import com.example.mxh.form.FormUpdateUser;
import com.example.mxh.model.user.User;
import com.example.mxh.model.user.UserDto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User map(FormCreateUser form) throws ParseException {
        var user = new User();
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setGioiTinh(form.getGioiTinh());
        user.setNgaySinh(form.getNgaySinh());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        return user;
    }
    public static UserDto map(User user){
        var dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setNgaySinh(user.getNgaySinh());
        dto.setGioiTinh(user.getGioiTinh());
        dto.setFollowing(user.getFollowing());
        dto.setFollower(user.getFollower());
        dto.setBackGround(user.getBackGround());
        dto.setAvatar(user.getAvatar());

//        dto.setSavePost(PostMapper.map(user.getSavePost()));

        return dto;
    }
    public static User  map(User user, FormUpdateUser formUpdateUser) throws ParseException {

        user.setEmail(formUpdateUser.getEmail() == null ? user.getEmail() : formUpdateUser.getEmail());
        user.setNgaySinh(formUpdateUser.getNgaySinh() == null? String.valueOf(user.getNgaySinh()) : formUpdateUser.getNgaySinh());
        user.setFirstName(formUpdateUser.getFirstName() == null ? user.getFirstName() : formUpdateUser.getFirstName());
        user.setLastName(formUpdateUser.getLastName() == null ? user.getLastName() : formUpdateUser.getLastName());
        user.setBackGround(formUpdateUser.getBackGround() == null ? user.getBackGround() : formUpdateUser.getBackGround());
        user.setAvatar(formUpdateUser.getAvatar() == null ? user.getAvatar() : formUpdateUser.getAvatar());
        return user;
    }

    public static List<UserDto> map(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(map(user));
        }
        return userDtos;
    }
}
