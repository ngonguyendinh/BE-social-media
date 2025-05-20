package com.example.mxh.model.notification;

import com.example.mxh.model.post.Post;
import com.example.mxh.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String type;
    @ManyToOne
    private Post newPost;
    @ManyToOne
    private User user;
}
