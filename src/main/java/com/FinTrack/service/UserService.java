package com.FinTrack.service;

import com.FinTrack.requests.UserRequest;

public interface UserService {
    String createUser(UserRequest userRequest);

    boolean deleteUser(Long id);
}
