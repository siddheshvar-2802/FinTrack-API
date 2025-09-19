package com.FinTrack.service.serviceImpl;

import com.FinTrack.model.User;
import com.FinTrack.repository.UserRepository;
import com.FinTrack.requests.UserRequest;
import com.FinTrack.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public String createUser(UserRequest userRequest) {
        try {
            logger.info("Creating user: {}", userRequest);

            if (userRepository.existsByEmail(userRequest.getEmail())) {
                logger.warn("User with email {} already exists", userRequest.getEmail());
                throw new IllegalArgumentException("User with this email already exists..️");
            }

            User user = new User();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            user.setRole(userRequest.getRole());

            userRepository.save(user);
            return "User created successfully";
        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to create user cause: {}", e.getMessage());
            throw new RuntimeException("Failed to create user cause: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            logger.info("Deleting user with id: {}", id);
            // TODO: Add actual user deletion logic here
            return true; // Return false if user not found
        } catch (Exception e) {
            logger.error("Error deleting user with id: {}", id, e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}