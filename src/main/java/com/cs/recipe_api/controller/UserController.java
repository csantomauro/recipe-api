package com.cs.recipe_api.controller;

import com.cs.recipe_api.model.User;
import com.cs.recipe_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(username));
    }
}
