package com.example.mxh.service.message;

import com.example.mxh.form.FormCreatMessage;
import com.example.mxh.model.chat.Chat;
import com.example.mxh.model.message.Message;
import com.example.mxh.model.user.User;
import com.example.mxh.repository.ChatRepository;
import com.example.mxh.repository.MessageRepository;
import com.example.mxh.service.chat.IChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MessageService implements IMessageService{
    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private IChatService chatService;
    @Override
    public Message createMessage(User user, int idChat, FormCreatMessage  form) throws Exception {
        Chat chat = chatService.findChatById(idChat);
        Message message = new Message();
        message.setChat(chat);
        message.setContent(form.getContent());
        message.setImage(form.getImage());
        message.setUser(user);
        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(message);
        chatRepository.save(chat);
        return savedMessage;
    }

    @Override
    public List<Message> findChatsMessages(int idChat) throws Exception {
        Chat chat = chatService.findChatById(idChat);

        return messageRepository.findByChatId(idChat);
    }
}
