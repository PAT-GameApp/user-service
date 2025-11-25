package com.cognizant.userService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.userService.Repository.UserServiceRepository;
import com.cognizant.userService.dto.UserRegisterResponseDTO;
import com.cognizant.userService.entity.UserServiceEntity;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserServiceRepository userServiceRepository;

    @Override
    public UserRegisterResponseDTO createUser(UserServiceEntity user) {
        user = userServiceRepository.save(user);
        UserRegisterResponseDTO response = UserRegisterResponseDTO.builder()
                .user_id(user.getUser_id())
                .user_name(user.getUser_name())
                .email(user.getEmail())
                .phone_number(user.getPhone_number())
                .role(user.getRole())
                .department(user.getDepartment())
                .office_location(user.getOffice_location())
                .build();

        return response;
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
