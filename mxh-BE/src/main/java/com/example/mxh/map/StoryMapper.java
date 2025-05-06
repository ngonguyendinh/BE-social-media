package com.example.mxh.map;

import com.example.mxh.form.FormCreateStory;
import com.example.mxh.model.story.Story;
import com.example.mxh.model.story.StoryDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoryMapper {
    public static Story map(FormCreateStory form){
        Story story = new Story();
        story.setCaption(form.getCaption());
        story.setImage(form.getImg());
        return story;
    }
    public static StoryDto map(Story story){
        StoryDto dto = new StoryDto();
        dto.setId(story.getId());
        dto.setCaption(story.getCaption());
        dto.setImage(story.getImage());
        dto.setUser(UserMapper.map(story.getUser()));
        dto.setTimeStamp(story.getTimeStamp());
        return dto;
    }
    public static List<StoryDto> map(List<Story> stories){
        List<StoryDto> storyDtos = new ArrayList<>();
        for (Story story : stories) {
            StoryDto storyDto = map(story);
            storyDtos.add(storyDto);
        }
        return storyDtos;
    }
}
