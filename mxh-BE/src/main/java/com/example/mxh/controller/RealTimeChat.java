package com.example.mxh.controller;

import com.example.mxh.map.MessageMapper;
import com.example.mxh.model.message.Message;
import com.example.mxh.model.message.MessageDto;
import com.example.mxh.model.notification.Notification;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class RealTimeChat {
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/chat/{groupId}")
    public Message sendToUser(@Payload Message message,
                                 @DestinationVariable String groupId){
        simpMessagingTemplate.convertAndSendToUser(groupId,"/private",message);
        return message;
    }



}
