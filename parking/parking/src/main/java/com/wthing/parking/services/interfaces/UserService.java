package com.wthing.parking.services.interfaces;

import com.wthing.parking.dto.UserDto;
import com.wthing.parking.dto.auth.AuthRequest;
import com.wthing.parking.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User save(User user);
    User create(User user);

    void registerNewUser(AuthRequest request);

    User getByUsername(String username);

    User getCurrentUser();

    List<UserDto> getAllUsers();

    UserDto getById(Long userId);

    void deleteById(Long userId);

    User banUser(Long userId);

    User updateUser(Long userId, UserDto userDto);
}
