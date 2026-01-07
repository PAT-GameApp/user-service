package com.cognizant.userService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.userService.Service.UserService;
import com.cognizant.userService.dto.UserRegisterRequestDTO;
import com.cognizant.userService.dto.UserRegisterResponseDTO;
import com.cognizant.userService.entity.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserRegisterResponseDTO> createUser(@Valid @RequestBody UserRegisterRequestDTO user) {
        UserRegisterResponseDTO userResponse = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String direction) {
        List<String> allowedSort = java.util.Arrays.asList("userId", "userName", "email", "phoneNumber", "role", "department", "locationId");
        Pageable pageable = com.cognizant.userService.util.PagingUtil.buildPageable(page, size, sort, direction, allowedSort);
        if (pageable != null) {
            Page<User> p = userService.getAllUsers(pageable);
            HttpHeaders headers = com.cognizant.userService.util.PagingUtil.buildHeaders(p);
            return ResponseEntity.ok().headers(headers).body(p.getContent());
        }
        Sort sortSpec = com.cognizant.userService.util.PagingUtil.buildSort(sort, direction, allowedSort);
        return ResponseEntity.ok(userService.getAllUsers(sortSpec));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
            @Valid @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
