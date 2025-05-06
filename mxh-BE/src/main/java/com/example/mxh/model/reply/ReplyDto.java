package com.example.mxh.model.reply;



import com.example.mxh.model.comment.CommentDto;
import com.example.mxh.model.user.UserDto;
import lombok.Data;

@Data
public class ReplyDto {
  private int id;
  private String content;
  private CommentDto comment;
  private UserDto user;
}
