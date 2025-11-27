package com.cognizant.userService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.userService.Repository.UserRepository;
import com.cognizant.userService.dto.UserRegisterResponseDTO;
import com.cognizant.userService.entity.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserRegisterResponseDTO createUser(User user) {
        user = userRepository.save(user);
        UserRegisterResponseDTO response = UserRegisterResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .department(user.getDepartment())
                .officeLocation(user.getOfficeLocation())
                .build();

        return response;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User updateUser(Long id, User updatedUser) {
        if (userRepository.existsById(id)) {
            updatedUser.setUserId(id);
            return userRepository.save(updatedUser);
        }
        return null;
    }

    public User deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        userRepository.delete(user);
        return user;
    }
}
