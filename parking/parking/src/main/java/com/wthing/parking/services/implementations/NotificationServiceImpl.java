package com.wthing.parking.services.implementations;

import com.wthing.parking.dto.NotificationDto;
import com.wthing.parking.mappers.Mappers;
import com.wthing.parking.models.Notification;
import com.wthing.parking.models.User;
import com.wthing.parking.repositories.NotificationRepo;
import com.wthing.parking.repositories.UserRepo;
import com.wthing.parking.services.interfaces.NotificationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.wthing.parking.constants.Messages.*;

public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepo notificationRepo;
    private final UserRepo userRepo;

    public NotificationServiceImpl(NotificationRepo notificationRepo, UserRepo userRepo) {
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepo.findAll();

        return notifications.stream()
                .map(Mappers::mapToNotificationDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto getById(Long notificationId) {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException(NOTIFICATION_NOT_FOUND));

        return Mappers.mapToNotificationDto(notification);
    }

    @Override
    public Notification create(NotificationDto notificationDto) {
        Notification notification = new Notification();

        User user = userRepo.findById(notificationDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        if (user != null) {
            notification.setUser(user);
            notification.setMessage(notificationDto.getMessage());
            notification.setTimestamp(LocalDateTime.now());
        }

        return notification;
    }

    @Override
    public void deleteById(Long notificationId) {
        notificationRepo.deleteById(notificationId);
    }
}
