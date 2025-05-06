package com.example.mxh.service.comment;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateComment;
import com.example.mxh.model.comment.Comment;
import com.example.mxh.model.comment.CommentDto;

public interface ICommentService {
    CommentDto create(FormCreateComment form, int idPost, int idUser) throws UserException;
    CommentDto likedComment(int commentId, int idUser) throws Exception;
    Comment findCommentById(int commentId) throws Exception;
}
