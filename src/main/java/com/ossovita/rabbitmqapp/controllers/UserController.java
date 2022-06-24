package com.ossovita.rabbitmqapp.controllers;

import com.ossovita.rabbitmqapp.business.abstracts.UserService;
import com.ossovita.rabbitmqapp.core.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userService.save(user);

    }

    @GetMapping("/users/create-dummy-user-request")
    public ResponseEntity<String> createDummyUserRequest(@RequestParam int piece) {
        userService.createDummyUserRequest(piece);
        return ResponseEntity.ok("Your request has been added to queue");
    }

}
