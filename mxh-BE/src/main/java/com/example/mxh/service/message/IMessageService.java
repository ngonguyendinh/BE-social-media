package com.example.mxh.service.message;

import com.example.mxh.form.FormCreatMessage;
import com.example.mxh.model.message.Message;
import com.example.mxh.model.user.User;

import java.util.List;

public interface IMessageService {
    Message createMessage(User user, int idChat, FormCreatMessage form) throws Exception;

    List<Message> findChatsMessages(int idChat) throws Exception;
}
