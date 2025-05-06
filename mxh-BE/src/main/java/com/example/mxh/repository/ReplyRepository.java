package com.example.mxh.repository;

import com.example.mxh.model.reply.Reply;
import com.example.mxh.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    List<Reply> findByUser(User user);
}
