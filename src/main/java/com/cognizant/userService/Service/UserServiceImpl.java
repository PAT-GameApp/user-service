package com.cognizant.userService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.userService.Repository.UserServiceRepository;
import com.cognizant.userService.entity.UserServiceEntity;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserServiceRepository userServiceRepository;

    @Override
    public UserServiceEntity createUser(UserServiceEntity user) {
        return userServiceRepository.save(user);
    }

    @Override
    public List<UserServiceEntity> getAllUsers() {
        return userServiceRepository.findAll();
    }

    @Override
    public UserServiceEntity getUserById(Long id) {
        Optional<UserServiceEntity> user = userServiceRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public UserServiceEntity updateUser(Long id, UserServiceEntity updatedUser) {
        if (userServiceRepository.existsById(id)) {
            updatedUser.setUser_id(id);
            return userServiceRepository.save(updatedUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userServiceRepository.deleteById(id);
    }
}