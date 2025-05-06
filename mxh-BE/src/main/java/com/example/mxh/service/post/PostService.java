//package com.example.mxh.service.post;
//
//import com.example.mxh.exception.UserException;
//import com.example.mxh.form.FormCreatePost;
//import com.example.mxh.map.CommentMapper;
//import com.example.mxh.map.PostMapper;
//import com.example.mxh.model.notification.Notification;
//import com.example.mxh.model.post.Post;
//import com.example.mxh.model.post.PostDto;
//import com.example.mxh.model.user.User;
//import com.example.mxh.repository.NotificationRepository;
//import com.example.mxh.repository.PostRepository;
//import com.example.mxh.repository.UserRepository;
//import com.example.mxh.service.notification.INotificationService;
//import com.example.mxh.service.user.IUserService;
//import lombok.AllArgsConstructor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//@Service
//@AllArgsConstructor
//public class PostService implements IPostService{
//    private PostRepository postRepository;
//    private UserRepository userRepository;
//    private IUserService userService;
//    private INotificationService notificationService;
//    private NotificationRepository notificationRepository;
//    private SimpMessagingTemplate messagingTemplate;
//    @Override
//    public Post createPost(FormCreatePost form, int idUser) throws UserException {
//        User user = userService.findById(idUser);
//        Post newPost = PostMapper.map(form);
//
//        Set<User> followers = userService.findUsersByIds(user.getFollowing());
//        String message = "Có bài đăng mới từ " + user.getFirstName() + " " + user.getLastName();
//        notificationService.createPost(user,followers,message);
//        return postRepository.save(newPost);
//    }
//
//    @Async
//    public void sendNotification(User follower, Notification notification) {
//        messagingTemplate.convertAndSendToUser(follower.getUsername(), "/notification", notification);
//    }
//    @Override
//    public List<PostDto> findByUserId(int idUser) {
//        List<PostDto> postDtos = new ArrayList<>();
//        var user = userRepository.findById(idUser).get();
//        var posts = postRepository.findByUser(user);
//        for (Post post : posts) {
//            postDtos.add(PostMapper.map(post));
//        }
//        return postDtos;
//    }
//
//    @Override
//    public List<PostDto> findAll() {
//        List<Post> posts = postRepository.findAll();
//        List<PostDto> postDtos = new ArrayList<>();
//        for (Post post : posts) {
//            var comment = post.getComments();
//            var dto = PostMapper.map(post);
//            dto.setComments(CommentMapper.map(comment));
//            postDtos.add(dto);
//        }
//        return postDtos ;
//    }
//
//    @Override
//    public void deletePost(int idPost, int idUser) throws Exception {
//        var post = postRepository.findById(idPost).get();
//        if (post.getUser().getId() != idUser){
//            throw new Exception("error please try again");
//        }
//        postRepository.deleteById(idPost);
//    }
//
//    @Override
//    public Post findById(int idPost) {
//        return postRepository.findById(idPost).get();
//    }
//
//    @Override
//    public PostDto savePost(int idPost, int idUser) {
//        var post = postRepository.findById(idPost).get();
//        var user = userRepository.findById(idUser).get();
//        var dto = PostMapper.map(post);
//        if(!user.getSavePost().contains(post)){
//            user.getSavePost().add(post);
//        }
//        else user.getSavePost().remove(post);
//        userRepository.save(user);
//        return dto;
//    }
//    @Transactional
//    @Override
//    public Post likePost(int idPost, int idUser) {
//        Post post = postRepository.findById(idPost)
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài đăng với id: " + idPost));
//        User user = userRepository.findById(idUser)
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + idUser));
//
//        if(post.getLike().contains(user)){
//            post.getLike().remove(user);
//        }
//        else{ post.getLike().add(user);}
//        return postRepository.save(post);
//    }
//}
package com.example.mxh.service.post;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreatePost;
import com.example.mxh.map.CommentMapper;
import com.example.mxh.map.PostMapper;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.post.Post;
import com.example.mxh.model.post.PostDto;
import com.example.mxh.model.user.User;
import com.example.mxh.repository.NotificationRepository;
import com.example.mxh.repository.PostRepository;
import com.example.mxh.repository.UserRepository;
import com.example.mxh.service.notification.INotificationService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PostService implements IPostService{
    private PostRepository postRepository;
    private UserRepository userRepository;
    private IUserService userService;
    private INotificationService notificationService;
    private NotificationRepository notificationRepository;
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public Post createPost(FormCreatePost form, int idUser) throws UserException {
        User user = userService.findById(idUser);
        if (user == null) {
            throw new UserException("User not found with id: " + idUser);
        }

        Post newPost = PostMapper.map(form);
        newPost.setUser(user); // Đảm bảo post được liên kết với user
        Post savedPost = postRepository.save(newPost);

        try {


            // Tạo thông báo nếu có người theo dõi
            String message = "Có bài đăng mới từ " + user.getFirstName() + " " + user.getLastName();
            notificationService.createPost(user, message);
        } catch (Exception e) {
            // Log lỗi nhưng vẫn tiếp tục lưu bài đăng
            System.err.println("Error creating notification: " + e.getMessage());
            e.printStackTrace();
        }

        return savedPost;
    }

    @Async
    public void sendNotification(User follower, Notification notification) {
        if (follower != null && notification != null) {
            try {
                messagingTemplate.convertAndSendToUser(follower.getUsername(), "/notification", notification);
            } catch (Exception e) {
                System.err.println("Error sending notification to user " + follower.getUsername() + ": " + e.getMessage());
            }
        }
    }

    @Override
    public List<PostDto> findByUserId(int idUser) {
        List<PostDto> postDtos = new ArrayList<>();
        var user = userRepository.findById(idUser).orElse(null);
        if (user != null) {
            var posts = postRepository.findByUser(user);
            for (Post post : posts) {
                postDtos.add(PostMapper.map(post));
            }
        }
        return postDtos;
    }

    @Override
    public List<PostDto> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            var comment = post.getComments();
            var dto = PostMapper.map(post);
            dto.setComments(CommentMapper.map(comment));
            postDtos.add(dto);
        }
        return postDtos ;
    }

    @Override
    public void deletePost(int idPost, int idUser) throws Exception {
        var post = postRepository.findById(idPost).orElse(null);
        if (post == null) {
            throw new Exception("Post not found");
        }
        if (post.getUser().getId() != idUser){
            throw new Exception("You don't have permission to delete this post");
        }
        postRepository.deleteById(idPost);
    }

    @Override
    public Post findById(int idPost) {
        return postRepository.findById(idPost).orElse(null);
    }

    @Override
    public PostDto savePost(int idPost, int idUser) {
        var post = postRepository.findById(idPost).orElse(null);
        var user = userRepository.findById(idUser).orElse(null);

        if (post == null || user == null) {
            return null;
        }

        var dto = PostMapper.map(post);
        if(!user.getSavePost().contains(post)){
            user.getSavePost().add(post);
        }
        else user.getSavePost().remove(post);
        userRepository.save(user);
        return dto;
    }

    @Transactional
    @Override
    public Post likePost(int idPost, int idUser) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài đăng với id: " + idPost));
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + idUser));


        if(post.getLike().contains(user)){
            post.getLike().remove(user);
        }
        else{
            post.getLike().add(user);
        }
        return postRepository.save(post);
    }
}
