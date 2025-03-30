package com.example.reviewerspring.api;

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
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        userService.login(request.getUserId(), request.getPassword());
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/users/{userPk}/preferred-tags")
    public ResponseEntity<List<UserTagPreferred>> getPreferredTags(@PathVariable Integer userPk) {
        return ResponseEntity.ok(userService.getPreferredTags(userPk));
    }

    @GetMapping("/users/{userPk}/tag-relate")
    public ResponseEntity<List<UserTagRelate>> getTagRelateData(@PathVariable Integer userPk) {
        return ResponseEntity.ok(userService.getTagRelateData(userPk));
    }

    @PostMapping("/users/{userId}/wishlist/{gameId}")
    public ResponseEntity<String> addToWishlist(@PathVariable String userId, @PathVariable String gameId) {
        userService.addToWishlist(userId, gameId);
        return ResponseEntity.ok("Game added to wishlist");
    }

    @DeleteMapping("/users/{userId}/wishlist/{gameId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable String userId, @PathVariable String gameId) {
        userService.removeFromWishlist(userId, gameId);
        return ResponseEntity.ok("Game removed from wishlist");
    }
}

@Getter
@Setter
class LoginRequest {
    private String userId;
    private String password;
}
