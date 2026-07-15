package com.cs.recipe_api.service;

import com.cs.recipe_api.model.User;
import com.cs.recipe_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken: " + username);
        }
        User user = new User();
        user.setUsername(username);
        return userRepository.save(user);
    }
}