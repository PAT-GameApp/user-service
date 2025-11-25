package com.cognizant.userService.Service;

import com.cognizant.userService.dto.UserRegisterResponseDTO;
import com.cognizant.userService.entity.UserServiceEntity;

public interface UserService {
    UserRegisterResponseDTO createUser(UserServiceEntity user);

    java.util.List<UserServiceEntity> getAllUsers();

    UserServiceEntity getUserById(Long id);

    UserServiceEntity updateUser(Long id, UserServiceEntity user);

    void deleteUser(Long id);
}
