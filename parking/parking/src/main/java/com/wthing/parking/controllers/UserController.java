package com.wthing.parking.controllers;

import com.wthing.parking.dto.UserDto;
import com.wthing.parking.models.User;
import com.wthing.parking.services.implementations.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Управление пользователями")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Operation(summary = "Получения списка всех пользователей")
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDto userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
