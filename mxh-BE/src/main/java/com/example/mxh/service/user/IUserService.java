package com.example.mxh.service.user;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateUser;
import com.example.mxh.form.FormUpdateUser;
import com.example.mxh.model.user.User;
import com.example.mxh.model.user.UserDto;
import com.example.mxh.response.AuthResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface IUserService {
    AuthResponse create(FormCreateUser form) throws UserException, Exception;
    User findById(int id) throws UserException;
    Page<UserDto> findAll(Pageable pageable);
    User update(int id, User user) throws UserException, ParseException;
    UserDto followUser(int id, int idFollower)throws UserException;
    Set<User> findUsersByIds(Set<Integer> following);
    List<UserDto> searchUser(String keySearch);
    User findUserByJwt(String jwt) throws UserException;
}
