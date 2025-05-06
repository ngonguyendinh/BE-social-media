package com.example.mxh.model.user;

import com.example.mxh.model.post.Post;
import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", length = 100, nullable = false , unique = true)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "email", length = 255, nullable = false , unique = true)
    private String email;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "ngay_sinh", length = 150 , nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "gioi_tinh", length = 3 , nullable = false)
    private String gioiTinh;

    private String avatar;
    private String backGround;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Post> savePost = new HashSet<>();


    @Column(name = "follower")
    private Set<Integer> follower = new HashSet<>();
    @Column(name = "following")
    private Set<Integer> following = new HashSet<>() ;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "user")
    List<Post> posts;

    public LocalDate setNgaySinh(String ngaySinh) throws ParseException {
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.ngaySinh = LocalDate.parse(ngaySinh,formatter);
        return this.ngaySinh;
    }
}
