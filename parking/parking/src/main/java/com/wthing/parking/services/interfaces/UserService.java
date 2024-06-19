package com.wthing.parking.services.interfaces;

import com.wthing.parking.dto.UserDto;
import com.wthing.parking.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User save(User user);
    User create(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

    User getCurrentUser();

    void getAdmin();

    List<UserDto> getAllUsers();

    UserDto getById(Long userId);

    void deleteById(Long userId);

    User banUser(Long userId);

    User updateUser(Long userId, UserDto userDto);
}
