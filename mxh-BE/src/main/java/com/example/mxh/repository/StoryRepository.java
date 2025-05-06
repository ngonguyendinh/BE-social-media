package com.example.mxh.repository;

import com.example.mxh.model.story.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story,Integer> {
    List<Story> findByUserId(int idUser);
}
