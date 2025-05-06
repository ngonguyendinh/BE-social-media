package com.example.mxh.map;

import com.example.mxh.model.chat.Chat;
import com.example.mxh.model.chat.ChatDto;

import java.util.ArrayList;
import java.util.List;

public class ChatMapper {
    public static ChatDto map(Chat chat){
        ChatDto dto = new ChatDto();
        dto.setId(chat.getId());
        dto.setChat(chat.getChat());
        dto.setChatImg(chat.getChatImg());
        dto.setMessages(MessageMapper.map(chat.getMessages()));
        dto.setUsers(UserMapper.map(chat.getUsers()));
        return dto;
    }
    public static List<ChatDto> map(List<Chat> chats){
        List<ChatDto> chatDtos = new ArrayList<>();
        for (Chat chat : chats) {
            ChatDto dto = map(chat);
            chatDtos.add(dto);
        }
        return chatDtos;
    }
}
