package com.example.mxh.service.chat;

import com.example.mxh.model.chat.Chat;
import com.example.mxh.model.user.User;

import java.util.List;

public interface IChatService {
    Chat createChat(User reqUser, User user2);
    Chat findChatById(int id) throws Exception;
    List<Chat> findUserChat(int idUser);
}
