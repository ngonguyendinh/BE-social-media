package com.example.mxh.model.reels;

import com.example.mxh.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Setter
@Getter
public class Reels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String urlVideo;
    @Column
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

//    @OneToMany
//    private List<User> liked;
}
