package com.cognizant.userService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cognizant.userService.Service.UserService;
import com.cognizant.userService.dto.UserRegisterRequestDTO;
import com.cognizant.userService.dto.UserRegisterResponseDTO;
import com.cognizant.userService.entity.User;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_shouldReturn201() {
        UserRegisterRequestDTO request = UserRegisterRequestDTO.builder()
                .userName("john")
                .email("john.doe@test.com")
                .phoneNumber("9999999999")
                .role("USER")
                .department("IT")
                .locationId(1L)
                .build();

        UserRegisterResponseDTO responseDto = UserRegisterResponseDTO.builder()
                .userId(1L)
                .userName("john")
                .email("john.doe@test.com")
                .phoneNumber("9999999999")
                .role("USER")
                .department("IT")
                .locationId(1L)
                .build();

        when(userService.createUser(any(UserRegisterRequestDTO.class))).thenReturn(responseDto);

        ResponseEntity<UserRegisterResponseDTO> response = userController.createUser(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getUserId());
    }

    @Test
    void getAllUsers_shouldReturnOk() {
        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getUserById_found_shouldReturnOk() {
        Long id = 1L;
        User user = new User();
        user.setUserId(id);

        when(userService.getUserById(id)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getUserId());
    }

    @Test
    void getUserById_notFound_shouldReturn404() {
        Long id = 1L;
        when(userService.getUserById(id)).thenReturn(null);

        ResponseEntity<User> response = userController.getUserById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateUser_found_shouldReturnOk() {
        Long id = 1L;
        User updatedUser = new User();
        updatedUser.setUserId(id);

        when(userService.updateUser(eq(id), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(id, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getUserId());
    }

    @Test
    void updateUser_notFound_shouldReturn404() {
        Long id = 1L;
        User updatedUser = new User();

        when(userService.updateUser(eq(id), any(User.class))).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(id, updatedUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteUser_shouldReturnOk() {
        Long id = 1L;
        User user = new User();
        user.setUserId(id);

        when(userService.deleteUser(id)).thenReturn(user);

        ResponseEntity<User> response = userController.deleteUser(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getUserId());
    }
}
