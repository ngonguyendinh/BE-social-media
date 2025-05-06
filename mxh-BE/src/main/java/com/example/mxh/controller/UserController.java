package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormUpdateUser;
import com.example.mxh.map.UserMapper;
import com.example.mxh.model.user.User;
import com.example.mxh.model.user.UserDto;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private IUserService iUserService;
    @GetMapping
    public Page<UserDto> findAll(Pageable pageable){
        return iUserService.findAll(pageable);
    }
    @GetMapping("/search")
    public List<UserDto> searchUser(@RequestParam("query") String query){
        return iUserService.searchUser(query);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") int id) throws Exception {
        if (iUserService.findById(id) == null){
            throw new Exception("User not exists");
        }
        return UserMapper.map(iUserService.findById(id));

    }
    @PutMapping("/profile")
    public ResponseEntity<UserDto> update(@RequestHeader("Authorization") String jwt, @RequestBody FormUpdateUser form) throws UserException, ParseException {
        User user = iUserService.findUserByJwt(jwt);
        User newUser = UserMapper.map(user,form);
        iUserService.update(user.getId(), newUser);
        if(user == null) {
           throw new UserException("not found user");
        }

        return new ResponseEntity<>(UserMapper.map(newUser),HttpStatus.OK);
    }
    @PutMapping("/follow/{idfollower}")
    public UserDto follow(@RequestHeader("Authorization") String jwt, @PathVariable("idfollower") int idFollower) throws UserException {
        User user = iUserService.findUserByJwt(jwt);
        return  iUserService.followUser(user.getId(), idFollower);
    }
    @GetMapping("/profile")
    public UserDto getUserFromToken(@RequestHeader("Authorization") String jwt) throws UserException {
//        System.out.println("jwt--------"+jwt);
        return UserMapper.map(iUserService.findUserByJwt(jwt));
    }
}
