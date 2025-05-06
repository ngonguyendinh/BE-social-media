package com.example.mxh.repository;

import com.example.mxh.model.post.Post;
import com.example.mxh.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
}
