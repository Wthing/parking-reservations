package com.wthing.parking.controllers;

import com.wthing.parking.dto.NotificationDto;
import com.wthing.parking.models.Notification;
import com.wthing.parking.services.implementations.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "API for managing notifications")
public class NotificationController {

    @Autowired
    private final NotificationServiceImpl notificationService;

    @Operation(summary = "Get all notifications")
    @GetMapping
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @Operation(summary = "Get a notification by ID")
    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        return notificationService.getById(id);
    }

    @Operation(summary = "Create a new notification")
    @PostMapping
    public Notification createNotification(@RequestBody NotificationDto notificationDto) {
        return notificationService.create(notificationDto);
    }

    @Operation(summary = "Delete a notification by ID")
    @DeleteMapping("/{id}")
    public void deleteNotificationById(@PathVariable Long id) {
        notificationService.deleteById(id);
    }
}
