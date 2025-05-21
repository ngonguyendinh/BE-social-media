package com.example.mxh.service.reels;

import com.example.mxh.exception.UserException;
import com.example.mxh.form.FormCreateReel;
import com.example.mxh.map.ReelsMapper;
import com.example.mxh.model.reels.Reels;
import com.example.mxh.model.reels.ReelsDto;
import com.example.mxh.model.user.User;
import com.example.mxh.repository.ReelsRepository;
import com.example.mxh.service.notification.INotificationService;
import com.example.mxh.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ReelsService implements IReelsService{
    private ReelsRepository reelsRepository;
    private IUserService userService;
    private INotificationService notificationService;
    @Override
    public ReelsDto createReels(FormCreateReel form, User user) {
        Reels reels = ReelsMapper.map(form);
        reels.setUser(user);
        Reels saveReels = reelsRepository.save(reels);
        try {
            // Tạo thông báo nếu có người theo dõi
            String message = "Đã đăng reels mới ";
            notificationService.createNotificationCreateReels(user, message );
        } catch (Exception e) {
            // Log lỗi nhưng vẫn tiếp tục lưu bài đăng
            System.err.println("Error creating notification: " + e.getMessage());
            e.printStackTrace();
        }
        return ReelsMapper.map(saveReels);
    }

    @Override
    public List<ReelsDto> findAll() {
        return ReelsMapper.map(reelsRepository.findAll());
    }

    @Override
    public List<ReelsDto> findUsersReel(int userId) throws UserException {
        userService.findById(userId);
        return ReelsMapper.map(reelsRepository.findByUserId(userId));
    }
}
