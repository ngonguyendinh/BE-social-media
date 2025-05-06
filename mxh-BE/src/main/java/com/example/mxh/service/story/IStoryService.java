package com.example.mxh.service.story;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateStory;
import com.example.mxh.model.story.Story;
import com.example.mxh.model.user.User;

import java.util.List;

public interface IStoryService {
    Story createStory(FormCreateStory form, User user);
    List<Story> findStoryByUser(int idUser) throws UserException;
}
