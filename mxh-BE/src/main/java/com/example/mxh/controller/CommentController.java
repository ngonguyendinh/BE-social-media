package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateComment;
import com.example.mxh.map.NotificationMapper;
import com.example.mxh.map.NotificationRecipientMapper;
import com.example.mxh.model.comment.CommentDto;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.notification.NotificationDto;
import com.example.mxh.model.notification.NotificationRecipient;
import com.example.mxh.model.notification.NotificationRecipientDto;
import com.example.mxh.model.post.Post;
import com.example.mxh.model.user.User;

import com.example.mxh.repository.NotificationRecipientRepository;
import com.example.mxh.service.comment.ICommentService;
import com.example.mxh.service.notification.INotificationService;
import com.example.mxh.service.post.IPostService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private ICommentService commentService;
    private IUserService userService;
    private IPostService postService;
    private INotificationService notificationService;
    private NotificationRecipientRepository notificationRecipientRepository;
    private SimpMessagingTemplate messagingTemplate;
    @PostMapping("/post/{idP}")
    public CommentDto create(@RequestBody FormCreateComment form, @PathVariable("idP") int idPost, @RequestHeader("Authorization") String jwt) throws UserException {



        User user = userService.findUserByJwt(jwt);
        Post post = postService.findById(idPost);

            if (post.getUser().getId() != user.getId()) {
                String message = " đã bình luận bài viết của bạn";
                try {
                    Notification notification = notificationService.createNotificationCommentPost(post.getUser(), user, message);
//                    NotificationRecipient recipient =  notificationRecipientRepository.findByNotificationId(notification.getId()).get();
//                    NotificationRecipientDto dto = NotificationRecipientMapper.map(recipient);
//                    messagingTemplate.convertAndSendToUser(
//                            post.getUser().getUsername(),
//                            "/notifications",
//                            dto
//                    );
                } catch (Exception e) {
                    System.err.println("Error creating notification: " + e.getMessage());
                }
            }
        CommentDto newComment = commentService.create(form, idPost, user.getId());

        messagingTemplate.convertAndSendToUser(
                post.getUser().getUsername(),
                "/posts/" + idPost + "/comments",
                newComment
        );
            messagingTemplate.convertAndSend(
                "/posts/" + idPost+"/comments",
                newComment
        );

           return newComment;
    }
    @PutMapping("/like/{idComment}")
    public CommentDto likeComment(@PathVariable("idComment") int idComment, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return commentService.likedComment(idComment,user.getId());
    }

}
