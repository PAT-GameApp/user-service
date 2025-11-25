package com.cognizant.userService.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterResponseDTO {
    private Long user_id;
    private String user_name;
    private String email;
    private String phone_number;
    private String role;
    private String department;
    private String office_location;
}
