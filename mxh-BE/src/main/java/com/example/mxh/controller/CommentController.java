package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateComment;
import com.example.mxh.model.comment.CommentDto;
import com.example.mxh.model.user.User;

import com.example.mxh.service.comment.ICommentService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private ICommentService commentService;
    private IUserService userService;
    @PostMapping("/post/{idP}")
    public CommentDto create(@RequestBody FormCreateComment form, @PathVariable("idP") int idPost, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return commentService.create(form,idPost,user.getId());
    }
    @PutMapping("/like/{idComment}")
    public CommentDto likeComment(@PathVariable("idComment") int idComment, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return commentService.likedComment(idComment,user.getId());
    }

}
