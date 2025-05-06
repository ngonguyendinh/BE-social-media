package com.example.mxh.map;

import com.example.mxh.form.FormCreateReel;
import com.example.mxh.model.reels.Reels;
import com.example.mxh.model.reels.ReelsDto;

import java.util.ArrayList;
import java.util.List;

public class ReelsMapper {
    public static ReelsDto map(Reels reels){
        ReelsDto dto = new ReelsDto();
        dto.setTitle(reels.getTitle());
        dto.setId(reels.getId());
        dto.setUser(UserMapper.map(reels.getUser()));
        dto.setUrlVideo(reels.getUrlVideo());
        dto.setCreatedAt(reels.getCreatedAt());
        return dto;
    }
    public static Reels map(FormCreateReel form){
        Reels reels = new Reels();
        reels.setTitle(form.getTitle());
        reels.setUrlVideo(form.getUrlVideo());
        return reels;
    }
    public static List<ReelsDto> map(List<Reels> reelses){
        List<ReelsDto> reelsDtos= new ArrayList<>();
        for (Reels reels : reelses) {
            ReelsDto reelsDto = map(reels);
            reelsDtos.add(reelsDto);
        }
        return reelsDtos;
    }
}
