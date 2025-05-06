package com.example.mxh.controller;

import com.example.mxh.form.FormCreatMessage;
import com.example.mxh.map.MessageMapper;
import com.example.mxh.model.message.Message;
import com.example.mxh.model.message.MessageDto;
import com.example.mxh.model.user.User;
import com.example.mxh.service.message.IMessageService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages/")
@AllArgsConstructor
public class MessageController {
    private IMessageService messageService;
    private IUserService userService;
    @PostMapping("/chat/{idChat}")
    public MessageDto createMessage(@RequestHeader("Authorization") String jwt
            , @RequestBody FormCreatMessage form
            , @PathVariable("idChat") int idChat) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Message message = messageService.createMessage(user,idChat,form);
        return MessageMapper.map(message);
    }
    @GetMapping("/chat/{idChat}")
    public List<MessageDto> findChatsMessages(@RequestHeader("Authorization") String jwt
            , @PathVariable("idChat") int idChat) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return MessageMapper.map(messageService.findChatsMessages(idChat));
    }
}
