package com.wthing.parking.services.implementations;

import com.wthing.parking.dto.UserDto;
import com.wthing.parking.dto.auth.AuthRequest;
import com.wthing.parking.mappers.Mappers;
import com.wthing.parking.models.User;
import com.wthing.parking.repositories.UserRepo;
import com.wthing.parking.services.interfaces.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.wthing.parking.constants.Messages.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }



    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    public String addUser(User userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userRepo.save(userInfo);
        return "User Added Successfully";
    }

    public User create(User user) {
        return userRepo.save(user);
    }

    @Override
    public void registerNewUser(AuthRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))  // Хешируем пароль перед сохранением
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .iin(request.getIin())
                .roles("ROLE_USER")
                .enabled(true)
                .build();
        userRepo.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
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
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        return Mappers.mapToUserDto(user);
    }

    @Override
    public void deleteById(Long userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public User banUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        user.setEnabled(false);

        return userRepo.save(user);
    }

    @Override
    public User updateUser(Long userId, UserDto userDto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        if (user != null) {
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setRoles(userDto.getRole());
            user.setIin(userDto.getIin());
            user.setEnabled(userDto.isEnabled());

            return userRepo.save(user);
        }

        return null;
    }

    @Override
    public boolean userExists(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    @Override
    public User makeUserAnOperator(Long id, UserDto userDto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        if (user != null) {
            user.setRoles(userDto.getRole());
            return userRepo.save(user);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepo.findByUsername(username);
        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
