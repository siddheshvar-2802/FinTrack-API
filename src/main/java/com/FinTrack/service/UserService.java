package com.FinTrack.service;

import com.FinTrack.requests.UserRequest;
import com.FinTrack.response.UserResponse;

import java.util.List;

public interface UserService {
    String createUser(UserRequest userRequest);

    boolean deleteUser(Long id);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);
}
