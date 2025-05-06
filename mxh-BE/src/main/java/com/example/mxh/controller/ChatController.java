package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateChat;
import com.example.mxh.map.ChatMapper;
import com.example.mxh.model.chat.Chat;
import com.example.mxh.model.chat.ChatDto;
import com.example.mxh.model.user.User;
import com.example.mxh.service.chat.IChatService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@AllArgsConstructor
public class ChatController {
    private IChatService chatService;
    private IUserService userService;
    @PostMapping
    public ChatDto createChat(@RequestHeader("Authorization") String jwt,@RequestBody FormCreateChat form) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        User user2 = userService.findById(form.getIdUser());
        Chat chat = chatService.createChat(reqUser,user2);
        return ChatMapper.map(chat);
    }
    @GetMapping
    public List<ChatDto> findUsersChat(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        List<Chat> chats = chatService.findUserChat(user.getId());
        return ChatMapper.map(chats);
    }
//    @GetMapping
//    public ChatDto findChatByUsersId(@PathVariable("Uid") int idUser){
//
//    }
}
