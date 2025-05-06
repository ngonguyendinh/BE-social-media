package com.example.mxh.service.story;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateStory;
import com.example.mxh.map.StoryMapper;
import com.example.mxh.model.story.Story;
import com.example.mxh.model.user.User;
import com.example.mxh.repository.StoryRepository;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoryService implements IStoryService {
    private StoryRepository storyRepository;
    private IUserService userService;
    @Override
    public Story createStory(FormCreateStory form, User user) {

        Story story = StoryMapper.map(form);
        story.setUser(user);

        return storyRepository.save(story);
    }

    @Override
    public List<Story> findStoryByUser(int idUser) throws UserException {
        User user = userService.findById(idUser);

        return storyRepository.findByUserId(idUser);
    }
}
