package com.example.mxh.model.story;

import com.example.mxh.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String caption;
    @Column
    private String image;
    @ManyToOne
    private User user;
    @Column
    @CreationTimestamp
    private LocalDateTime timeStamp;

}
