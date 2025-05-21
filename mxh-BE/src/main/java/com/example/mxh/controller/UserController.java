package com.example.mxh.controller;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormUpdateUser;
import com.example.mxh.map.NotificationRecipientMapper;
import com.example.mxh.map.UserMapper;
import com.example.mxh.model.notification.Notification;
import com.example.mxh.model.notification.NotificationRecipient;
import com.example.mxh.model.notification.NotificationRecipientDto;
import com.example.mxh.model.user.User;
import com.example.mxh.model.user.UserDto;
import com.example.mxh.repository.NotificationRecipientRepository;
import com.example.mxh.service.notification.INotificationService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private IUserService iUserService;
    private INotificationService notificationService;
    private SimpMessagingTemplate messagingTemplate;
    private NotificationRecipientRepository notificationRecipientRepository;
    @GetMapping
    public Page<UserDto> findAll(Pageable pageable){
        return iUserService.findAll(pageable);
    }
    @GetMapping("/search")
    public List<UserDto> searchUser(@RequestParam("query") String query){
        return iUserService.searchUser(query);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") int id) throws Exception {
        if (iUserService.findById(id) == null){
            throw new Exception("User not exists");
        }
        return UserMapper.map(iUserService.findById(id));

    }
    @PutMapping("/profile")
    public ResponseEntity<UserDto> update(@RequestHeader("Authorization") String jwt, @RequestBody FormUpdateUser form) throws UserException, ParseException {
        User user = iUserService.findUserByJwt(jwt);
        User newUser = UserMapper.map(user,form);
        iUserService.update(user.getId(), newUser);
        if(user == null) {
           throw new UserException("not found user");
        }

        return new ResponseEntity<>(UserMapper.map(newUser),HttpStatus.OK);
    }
    @PutMapping("/follow/{idfollower}")
    public UserDto follow(@RequestHeader("Authorization") String jwt, @PathVariable("idfollower") int idFollower) throws UserException {
        User user = iUserService.findUserByJwt(jwt);
        User follower = iUserService.findById(idFollower);
        boolean isAlreadyFollowing = user.getFollowing().contains(idFollower);
        UserDto result = iUserService.followUser(user.getId(), idFollower);
        if (user.getId() != idFollower && !isAlreadyFollowing) {
            String message = "đã follow bạn";
            try {
                Notification notification = notificationService.createNotificationFollowUser(user, follower, message);
                NotificationRecipient recipient = notificationRecipientRepository.findByNotificationId(notification.getId()).get();
                NotificationRecipientDto dto = NotificationRecipientMapper.map(recipient);

                // Gửi thông báo đến người được follow, không phải người thực hiện follow
                messagingTemplate.convertAndSendToUser(
                        follower.getUsername(),
                        "/notifications",
                        dto
                );
            } catch (Exception e) {
                System.err.println("Error creating notification: " + e.getMessage());
            }
        }

        return result;
    }

    @GetMapping("/profile")
    public UserDto getUserFromToken(@RequestHeader("Authorization") String jwt) throws UserException {
        return UserMapper.map(iUserService.findUserByJwt(jwt));
    }
    @GetMapping("/followers")
    public Set<UserDto> getFollowers(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = iUserService.findUserByJwt(jwt);

        return UserMapper.map(iUserService.findUsersByIds(user.getFollower()));
    }
}
