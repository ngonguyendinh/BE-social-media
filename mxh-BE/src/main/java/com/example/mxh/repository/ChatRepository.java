package com.example.mxh.repository;

import com.example.mxh.model.chat.Chat;
import com.example.mxh.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Integer> {
    List<Chat> findByUsersId(int idUser);
    @Query("select c from Chat c where :user member of c.users and :reqUser member of c.users")
    Chat findChatByUsersId(@Param("user")User user, @Param("reqUser") User reqUser);
}
