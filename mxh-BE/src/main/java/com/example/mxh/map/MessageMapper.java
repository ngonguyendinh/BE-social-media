package com.example.mxh.map;

import com.example.mxh.model.message.Message;
import com.example.mxh.model.message.MessageDto;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {
    public static MessageDto map(Message message){
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setImage(message.getImage());

        dto.setUser(UserMapper.map(message.getUser()));
        return dto;
    }
    public static List<MessageDto> map(List<Message> messages){
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            MessageDto dto = map(message);
            messageDtos.add(dto);
        }
        return messageDtos;
    }
}
