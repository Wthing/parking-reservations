package com.wthing.parking.services.implementations;

import com.wthing.parking.dto.UserDto;
import com.wthing.parking.enums.AuthoritiesEnum;
import com.wthing.parking.mappers.Mappers;
import com.wthing.parking.models.User;
import com.wthing.parking.repositories.UserRepo;
import com.wthing.parking.services.interfaces.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public User create(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Override
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(AuthoritiesEnum.ROLE_ADMIN);
        save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();

        return users.stream()
                .map(Mappers::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return Mappers.mapToUserDto(user);
    }

    @Override
    public void deleteById(Long userId) {
        userRepo.deleteById(userId);
    }
}
