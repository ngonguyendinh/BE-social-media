package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreatePost;
import com.example.mxh.map.PostMapper;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.post.Post;
import com.example.mxh.model.post.PostDto;
import com.example.mxh.model.user.User;
import com.example.mxh.service.notification.INotificationService;
import com.example.mxh.service.post.IPostService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private IPostService postService;
    private IUserService iUserService;
    private SimpMessagingTemplate messagingTemplate;
    private INotificationService notificationService;
    @PostMapping("/user")
    public ResponseEntity<PostDto> createPost(@RequestBody FormCreatePost form,@RequestHeader("Authorization") String jwt) throws UserException {
        User user = iUserService.findUserByJwt(jwt);
        Post post = postService.createPost(form, user.getId());
        return new ResponseEntity<>(PostMapper.map(post), HttpStatus.CREATED);
    }


    @DeleteMapping("/{postsId}")
    public ResponseEntity<String> deletePost(@PathVariable("postsId")int postId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = iUserService.findUserByJwt(jwt);

        postService.deletePost(postId, user.getId());
        return new ResponseEntity<>("the post is deleted successfull",HttpStatus.OK);
    }
    @GetMapping("/{Pid}")
    public ResponseEntity<PostDto> findByIdHandler(@PathVariable("Pid") int idPost){

        return  new ResponseEntity<>(PostMapper.map(postService.findById(idPost)),HttpStatus.ACCEPTED);
    }
    @GetMapping("/user/{Uid}")
    public ResponseEntity<List<PostDto>> findUsersPost(@PathVariable("Uid")int idUser){
        return new ResponseEntity<>(postService.findByUserId(idUser),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<PostDto>> findAll(){
        return new ResponseEntity<>(postService.findAll(),HttpStatus.OK);
    }
    @PutMapping("/save/{Pid}")
    public ResponseEntity<PostDto> savePostHandler(@PathVariable("Pid") int postId,@RequestHeader("Authorization") String jwt) throws Exception{
        User user = iUserService.findUserByJwt(jwt);
        return new ResponseEntity<>(postService.savePost(postId, user.getId()),HttpStatus.ACCEPTED);
    }
    @PutMapping("/like/{Pid}")
    public ResponseEntity<String> likePostHandler(@PathVariable("Pid") int postId,@RequestHeader("Authorization") String jwt) throws UserException {
//        User user = iUserService.findUserByJwt(jwt);
//        Post post = postService.findById(postId);
//
//
//        boolean wasLiked = post.getLike().stream().anyMatch(u -> u.getId() == user.getId());
//
//
//        post = postService.likePost(postId, user.getId());
//
//
//        boolean isNowLiked = post.getLike().stream().anyMatch(u -> u.getId() == user.getId());
//
//
//        if (!wasLiked && isNowLiked) {
//            // Only send notification if user is liking someone else's post
//            if (post.getUser().getId() != user.getId()) {
//                String message= (user.getFirstName() + " " + user.getLastName() + " đã thích bài viết của bạn  ");
//                Notification notification =notificationService.createNotificationLikePost(post.getUser(), user,message);
//                messagingTemplate.convertAndSendToUser(
//                        post.getUser().getUsername(),
//                        "/notification",notification
//                );
//            }
//            return ResponseEntity.ok("Bài viết đã được like!");
//        } else {
//            return ResponseEntity.ok("Bài viết đã được unlike!");
//        }
        try {
            User user = iUserService.findUserByJwt(jwt);
            Post post = postService.findById(postId);

            boolean wasLiked = post.getLike().stream().anyMatch(u -> u.getId() == user.getId());
            post = postService.likePost(postId, user.getId());
            boolean isNowLiked = post.getLike().stream().anyMatch(u -> u.getId() == user.getId());

            if (!wasLiked && isNowLiked) {
                // Only send notification if user is liking someone else's post
                if (post.getUser().getId() != user.getId()) {
                    String message = user.getFirstName() + " " + user.getLastName() + " đã thích bài viết của bạn";
                    try {
                        Notification notification = notificationService.createNotificationLikePost(post.getUser(), user, message);
                        messagingTemplate.convertAndSendToUser(
                                post.getUser().getUsername(),
                                "/notification",
                                notification
                        );
                    } catch (Exception e) {
                        // Log lỗi nhưng vẫn tiếp tục xử lý
                        System.err.println("Error creating notification: " + e.getMessage());
                    }
                }
                return ResponseEntity.ok("Bài viết đã được like!");
            } else {
                return ResponseEntity.ok("Bài viết đã được unlike!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xử lý like/unlike: " + e.getMessage());
        }

    }
}
