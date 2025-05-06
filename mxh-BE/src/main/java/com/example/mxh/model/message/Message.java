package com.example.mxh.model.message;

import com.example.mxh.model.chat.Chat;
import com.example.mxh.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String image;
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Chat chat;
    @CreationTimestamp
    private LocalDateTime timeStamp;

}
