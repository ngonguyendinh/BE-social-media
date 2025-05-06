package com.example.mxh.service.post;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreatePost;
import com.example.mxh.model.post.Post;
import com.example.mxh.model.post.PostDto;

import java.util.List;

public interface IPostService {
    Post createPost(FormCreatePost form, int idUser) throws UserException;
    List<PostDto> findByUserId(int idUser);
    List<PostDto> findAll();
    void deletePost(int idPost, int idUser) throws Exception;
    Post findById(int idPost);
    PostDto savePost(int idPost, int idUser);
    Post likePost(int idPost, int idUser);
}
