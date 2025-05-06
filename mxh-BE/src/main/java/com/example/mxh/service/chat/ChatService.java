package com.example.mxh.service.chat;

import com.example.mxh.model.chat.Chat;
import com.example.mxh.model.user.User;
import com.example.mxh.repository.ChatRepository;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService implements IChatService{
    private ChatRepository chatRepository;
    private IUserService userService;
    @Override
    public Chat createChat(User reqUser, User user2) {
        Chat isExists = chatRepository.findChatByUsersId(user2,reqUser);

        if(isExists != null )
        {
            return isExists;
        }
        Chat chat = new Chat();
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);

        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(int id) throws Exception {
        Optional<Chat> opt = chatRepository.findById(id);
        if (opt.isEmpty()){
            throw new Exception("chat not found with id" +id);
        }
        return opt.get();
    }

    @Override
    public List<Chat> findUserChat(int idUser) {

        return chatRepository.findByUsersId(idUser);
    }
}
