package com.FinTrack.cotroller;

import com.FinTrack.requests.UserRequest;
import com.FinTrack.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try {
            logger.info("Creating user: {}", userRequest);
            String created = userService.createUser(userRequest);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return ResponseEntity.status(500).body("Internal server error");
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
}