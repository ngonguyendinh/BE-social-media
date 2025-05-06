package com.example.mxh.model.chat;

import com.example.mxh.model.message.Message;
import com.example.mxh.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String chat;
    @Column
    private String chatImg;

    @ManyToMany
    private List<User> users = new ArrayList<>();
    @OneToMany(mappedBy = "chat")
    List<Message> messages= new ArrayList<>();
    @Column
    @CreationTimestamp
    private LocalDateTime timeStamp;
}
