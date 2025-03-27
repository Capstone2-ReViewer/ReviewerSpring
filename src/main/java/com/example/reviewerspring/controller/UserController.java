package com.example.reviewerspring.controller;

import com.example.reviewerspring.domain.User;
import com.example.reviewerspring.domain.UserTagPreferred;
import com.example.reviewerspring.domain.UserTagRelate;
import com.example.reviewerspring.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getUserId(), request.getPassword());
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/{userPk}/preferred-tags")
    public ResponseEntity<List<UserTagPreferred>> getPreferredTags(@PathVariable Integer userPk) {
        return ResponseEntity.ok(userService.getPreferredTags(userPk));
    }

    @GetMapping("/{userPk}/tag-relate")
    public ResponseEntity<List<UserTagRelate>> getTagRelateData(@PathVariable Integer userPk) {
        return ResponseEntity.ok(userService.getTagRelateData(userPk));
    }
}

@Getter
@Setter
class LoginRequest {
    private String userId;
    private String password;
}
