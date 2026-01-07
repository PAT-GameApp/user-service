package com.cognizant.userService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cognizant.userService.Repository.UserRepository;
import com.cognizant.userService.dto.UserRegisterRequestDTO;
import com.cognizant.userService.dto.UserRegisterResponseDTO;
import com.cognizant.userService.entity.User;
import com.cognizant.userService.exception.DuplicateEmailException;
import com.cognizant.userService.exception.DuplicatePhoneNumberException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserRegisterResponseDTO createUser(UserRegisterRequestDTO request) {
        // check if email exists
        if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + request.getEmail());
        }

        // check if phone number exists
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().trim().isEmpty()
                && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicatePhoneNumberException("Phone number already exists: " + request.getPhoneNumber());
        }
        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .department(request.getDepartment())
                .locationId(request.getLocationId())
                .build();

        user = userRepository.save(user);

        UserRegisterResponseDTO response = UserRegisterResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .department(user.getDepartment())
                .locationId(user.getLocationId())
                .build();
        return response;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> getAllUsers(Sort sort) {
        return sort == null || sort.isUnsorted() ? userRepository.findAll() : userRepository.findAll(sort);
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
