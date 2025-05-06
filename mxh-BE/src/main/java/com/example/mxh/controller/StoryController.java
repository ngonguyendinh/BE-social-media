package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateStory;
import com.example.mxh.map.StoryMapper;
import com.example.mxh.model.story.StoryDto;
import com.example.mxh.model.user.User;
import com.example.mxh.service.story.IStoryService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/stories")
public class StoryController {
    private IStoryService storyService;
    private IUserService userService;
    @PostMapping
    public StoryDto createStory(@RequestBody FormCreateStory form , @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return StoryMapper.map(storyService.createStory(form,user));
    }
    @GetMapping("/user/{Uid}")
    public List<StoryDto> findStoryByUserId(@PathVariable("Uid") int idUser,@RequestHeader("Authorization")String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return StoryMapper.map(storyService.findStoryByUser(idUser));
    }
}
