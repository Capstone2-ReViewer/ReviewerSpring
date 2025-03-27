package com.example.reviewerspring.service;

import com.example.reviewerspring.Repository.UserRepository;
import com.example.reviewerspring.Repository.UserTagPreferredRepository;
import com.example.reviewerspring.Repository.UserTagRelateRepository;
import com.example.reviewerspring.domain.User;
import com.example.reviewerspring.domain.UserTagPreferred;
import com.example.reviewerspring.domain.UserTagRelate;
import com.example.reviewerspring.domain.UserWishlist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTagPreferredRepository userTagPreferredRepository;
    private final UserTagRelateRepository userTagRelateRepository;

    public UserService(UserRepository userRepository, UserTagPreferredRepository userTagPreferredRepository, UserTagRelateRepository userTagRelateRepository) {
        this.userRepository = userRepository;
        this.userTagPreferredRepository = userTagPreferredRepository;
        this.userTagRelateRepository = userTagRelateRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User login(String userId, String password) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public List<UserTagPreferred> getPreferredTags(Integer userPk) {
        return userTagPreferredRepository.findByUserPk(userPk);
    }

    public List<UserTagRelate> getTagRelateData(Integer userPk) {
        return userTagRelateRepository.findByUserPk(userPk);
    }

    public void addToWishlist(String userId, String gameId) {

    }

    public void removeFromWishlist(String userId, String gameId) {

    }

    public List<UserWishlist> getWishList(String userId) {
        return null;
    }
}
