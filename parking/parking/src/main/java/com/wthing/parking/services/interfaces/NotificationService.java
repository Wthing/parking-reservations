package com.wthing.parking.services.interfaces;

import com.wthing.parking.dto.NotificationDto;
import com.wthing.parking.models.Notification;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getAllNotifications();

    NotificationDto getById(Long notificationId);

    Notification create(NotificationDto notificationDto);

    void deleteById(Long notificationId);
}
