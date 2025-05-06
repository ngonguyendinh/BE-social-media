package com.example.mxh.model.reply;

import com.example.mxh.model.comment.Comment;
import com.example.mxh.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    @ManyToOne
    private Comment comment;
    @OneToOne
    private User user;
}
