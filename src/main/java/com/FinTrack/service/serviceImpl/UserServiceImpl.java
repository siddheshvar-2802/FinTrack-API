package com.FinTrack.service.serviceImpl;

import com.FinTrack.model.Users;
import com.FinTrack.repository.UserRepository;
import com.FinTrack.requests.UserRequest;
import com.FinTrack.response.UserResponse;
import com.FinTrack.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

            Users users = new Users();
            users.setName(userRequest.getName());
            users.setEmail(userRequest.getEmail());
            users.setPassword(userRequest.getPassword());
            users.setRole(userRequest.getRole());

            userRepository.save(users);
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
            logger.info("Attempting to delete user with id: {}", id);
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                logger.info("User with id {} deleted successfully.", id);
                return true;
            } else {
                logger.warn("User with id {} not found for deletion.", id);
                throw new IllegalArgumentException("User not found with id: " + id);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting user with id: {}", id, e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    public UserResponse getUserById(Long id) {
        try {
            logger.info("Fetching user by id: {}", id);
            Users users = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
            return mapToResponse(users);
        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching user by id: {}", id, e);
            throw new RuntimeException("Failed to fetch user", e);
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {
        try {
            logger.info("Fetching all users");
            List<Users> users = userRepository.findAll();
            return users.stream()
                    .map(this::mapToResponse)
                    .toList();
        } catch (Exception e) {
            logger.error("Error fetching all users", e);
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    private UserResponse mapToResponse(Users users) {
        UserResponse response = new UserResponse();
        response.setId(users.getId());
        response.setUsername(users.getName());
        response.setEmail(users.getEmail());
        response.setRole(users.getRole());
        return response;
    }
}