package com.FinTrack.cotroller;

import com.FinTrack.requests.UserRequest;
import com.FinTrack.response.UserResponse;
import com.FinTrack.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        try {
            logger.info("Received request to create user: {}", userRequest);
            String result = userService.createUser(userRequest);
            return ResponseEntity.status(201).body(result);
        } catch (IllegalArgumentException e) {
            logger.warn("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            logger.info("Deleting user with id: {}", id);
            boolean deleted = userService.deleteUser(id);
            if (!deleted) {
                logger.warn("User not found for delete: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            logger.info("Received request to fetch user by id: {}", id);
            UserResponse userResponse = userService.getUserById(id);
            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            logger.warn("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error fetching user with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            logger.info("Fetching all users");
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}