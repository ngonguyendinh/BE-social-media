package com.example.mxh.service.comment;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateComment;
import com.example.mxh.map.CommentMapper;
import com.example.mxh.model.comment.Comment;
import com.example.mxh.model.comment.CommentDto;
import com.example.mxh.model.post.Post;
import com.example.mxh.model.user.User;
import com.example.mxh.repository.CommentRepository;
import com.example.mxh.repository.PostRepository;
import com.example.mxh.service.post.IPostService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService{
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private IUserService userService;
    private IPostService postService;
    @Override
    public CommentDto create(FormCreateComment form, int idPost, int idUser) throws UserException {
        User user =  userService.findById(idUser);
        Post post = postService.findById(idPost);
        Comment comment = new Comment();
        comment.setContent(form.getContent());
        comment.setUser(user);
        Comment saveComment = commentRepository.save(comment);
        post.getComments().add(saveComment);
        postRepository.save(post);
        return CommentMapper.map(saveComment);
    }

    @Override
    public CommentDto likedComment(int commentId, int idUser) throws Exception,UserException {
        Comment comment = findCommentById(commentId);
        User user = userService.findById(idUser);
        if (!comment.getLiked().contains(user)){
            comment.getLiked().add(user);
        }
        else comment.getLiked().remove(user);
        return CommentMapper.map(commentRepository.save(comment));
    }

    @Override
    public Comment findCommentById(int commentId) throws Exception {
        Optional<Comment> opt = commentRepository.findById(commentId);
        if (opt.isEmpty()){
            throw new Exception("comment not exists");
        }
        return opt.get();
    }
}
