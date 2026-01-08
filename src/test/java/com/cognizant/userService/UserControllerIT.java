package com.cognizant.userService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers_returnsOk() throws Exception {
        mockMvc.perform(get("/users/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getUserById_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(get("/users/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 200 && status != 404) {
                        throw new AssertionError("Expected 200 or 404 but got " + status);
                    }
                });
    }

    @Test
    void createUser_returnsCreatedOrBadRequest() throws Exception {
        String requestJson = "{" +
                "\"name\":\"Test User\"," +
                "\"email\":\"test@example.com\"" +
                "}";

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 201 && status != 400) {
                        throw new AssertionError("Expected 201 or 400 but got " + status);
                    }
                });
    }

    @Test
    void updateUser_returnsOkOrNotFoundOrBadRequest() throws Exception {
        String requestJson = "{" +
                "\"name\":\"Updated User\"," +
                "\"email\":\"updated@example.com\"" +
                "}";

        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 200 && status != 400 && status != 404) {
                        throw new AssertionError("Expected 200, 400 or 404 but got " + status);
                    }
                });
    }

    @Test
    void deleteUser_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 200 && status != 404) {
                        throw new AssertionError("Expected 200 or 404 but got " + status);
                    }
                });
    }
}