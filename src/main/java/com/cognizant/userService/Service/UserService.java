package com.cognizant.userService.Service;
import com.cognizant.userService.entity.UserServiceEntity;


public interface UserService {
    UserServiceEntity createUser(UserServiceEntity user);
    java.util.List<UserServiceEntity> getAllUsers();
    UserServiceEntity getUserById(Long id);
    UserServiceEntity updateUser(Long id, UserServiceEntity user);
    void deleteUser(Long id);
}