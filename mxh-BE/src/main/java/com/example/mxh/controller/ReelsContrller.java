package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateReel;
import com.example.mxh.model.reels.ReelsDto;
import com.example.mxh.model.user.User;
import com.example.mxh.service.reels.IReelsService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reels")
@AllArgsConstructor
public class ReelsContrller {
    private IReelsService reelsService;
    private IUserService userService;
    @PostMapping
    public ReelsDto createReel(@RequestBody FormCreateReel form,@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return reelsService.createReels(form,user);
    }
    @GetMapping
    public List<ReelsDto> findAll(){
        return reelsService.findAll();
    }
    @GetMapping("/users/{idU}")
    public List<ReelsDto> findUsersReel(@PathVariable("idU")int userId) throws UserException {
        return reelsService.findUsersReel(userId);
    }
}
