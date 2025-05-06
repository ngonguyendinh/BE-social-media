package com.example.mxh.service.reels;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateReel;
import com.example.mxh.model.reels.ReelsDto;
import com.example.mxh.model.user.User;

import java.util.List;

public interface IReelsService {
    ReelsDto createReels(FormCreateReel form, User user);
    List<ReelsDto> findAll();
    List<ReelsDto> findUsersReel(int userId) throws UserException;
}
