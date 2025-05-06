package com.example.mxh.map;

import com.example.mxh.form.FormCreateComment;
import com.example.mxh.model.comment.Comment;
import com.example.mxh.model.comment.CommentDto;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static Comment map(FormCreateComment form){
        Comment comment = new Comment();
        comment.setContent(form.getContent());
        return comment;
    }
    public static CommentDto map(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setUser(UserMapper.map(comment.getUser()));
        commentDto.setLiked(UserMapper.map(comment.getLiked()));
        commentDto.setImage(comment.getImage());
        commentDto.setCreatedAt(comment.getCreatedAt());
        return commentDto;
    }
    public static List<CommentDto> map(List<Comment> comments){
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(map(comment));
        }
        return commentDtos;
    }
}
