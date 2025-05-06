package com.example.mxh.model.post;

import com.example.mxh.model.comment.Comment;
import com.example.mxh.model.user.User;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "caption")
    private String caption;

    @Column(name = "img")
    private String image;

    @Column(name = "video")
    private String video;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<User> like = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();
}
