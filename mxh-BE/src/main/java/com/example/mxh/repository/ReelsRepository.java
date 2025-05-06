package com.example.mxh.repository;

import com.example.mxh.model.reels.Reels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelsRepository extends JpaRepository<Reels,Long> {
    List<Reels> findByUserId(int id);
}
