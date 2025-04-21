package com.example.reviewerspring.service;

import com.example.reviewerspring.Repository.UserRepository;
import com.example.reviewerspring.Repository.UserTagPreferredRepository;
import com.example.reviewerspring.Repository.UserTagRelateRepository;
import com.example.reviewerspring.Repository.UserWishlistRepository;
import com.example.reviewerspring.domain.User;
import com.example.reviewerspring.domain.UserTagPreferred;
import com.example.reviewerspring.domain.UserTagRelate;
import com.example.reviewerspring.domain.UserWishlist;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTagPreferredRepository userTagPreferredRepository;
    private final UserTagRelateRepository userTagRelateRepository;
    private final UserWishlistRepository userWishlistRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserTagPreferredRepository userTagPreferredRepository,
                       UserTagRelateRepository userTagRelateRepository, UserWishlistRepository userWishlistRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userTagPreferredRepository = userTagPreferredRepository;
        this.userTagRelateRepository = userTagRelateRepository;
        this.userWishlistRepository = userWishlistRepository;
        this.encoder = encoder;
    }

    public void registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User login(String userId, String password) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.filter(u -> u.getPassword().equals(password)).orElse(null);
    }

    public User logout() {
        return null;
    }

    public void logout(String userId) {
        // JWT 기반이면 토큰을 블랙리스트에 추가하는 로직을 구현 가능
        // 단순 로그아웃이면 클라이언트가 토큰을 삭제하는 방식 사용
    }

    public Optional<User> getUserInfo(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public Optional<User> updateUser(String userId, User updatedUser) {
        return userRepository.findByUserId(userId).map(user -> {
            user.setNickname(updatedUser.getNickname());
            user.setAge(updatedUser.getAge());
            user.setGender(updatedUser.getGender());
            return userRepository.save(user);
        });
    }

    public void deleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }

    public List<UserTagPreferred> getPreferredTags(Integer userPk) {
        return userTagPreferredRepository.findByUserPk(userPk);
    }

    public List<UserTagRelate> getTagRelateData(Integer userPk) {
        return userTagRelateRepository.findByUserPk(userPk);
    }

    public void addToWishlist(String userId, String gameId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserWishlist wishlist = new UserWishlist();
        wishlist.setUser(user);
        userWishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(String userId, String gameId) {
        userWishlistRepository.deleteByUserIdAndGameId(userId, gameId);
    }

    public List<UserWishlist> getWishList(String userId) {
        return userWishlistRepository.findByUserId(userId);
    }
}
