package com.example.reviewerspring.service;

import com.example.reviewerspring.domain.*;
import com.example.reviewerspring.dto.UserSignupRequest;
import com.example.reviewerspring.repository.TagRepository;
import com.example.reviewerspring.repository.UserRepository;
import com.example.reviewerspring.repository.UserTagPreferredRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserTagPreferredRepository preferredRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(UserSignupRequest request) {
        // 입력 검증
        if (request.getUserId() == null || request.getPassword() == null || request.getPasswordCheck() == null
                || request.getName() == null || request.getNickname() == null
                || request.getAge() == null || request.getGender() == null
                || request.getPreferredTags() == null || request.getDislikedTags() == null) {
            throw new IllegalArgumentException("모든 항목을 입력해주세요.");
        }

        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (!isUserIdAvailable(request.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // User 저장
        User user = new User();
        user.setUserId(request.getUserId());
        user.setPassword(encodedPassword);  // 암호화된 비밀번호 저장
        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setAge(request.getAge());
        user.setGender(request.getGender());

        User savedUser = userRepository.save(user);
        String userId = savedUser.getId();

        // 선호 / 비선호 태그 저장
        for (Integer tagId : request.getPreferredTags()) {
            UserTagPreferred tag = new UserTagPreferred();
            tag.setUserId(userId);
            tag.setTagId(tagId);
            preferredRepository.save(tag);
        }
        for (Integer tagId : request.getDislikedTags()) {
            UserTagPreferred tag = new UserTagPreferred();
            tag.setUserId(userId);
            tag.setTagId(tagId);
            preferredRepository.save(tag); // 비선호도 같은 테이블에 저장됨
        }
    }

    public boolean isUserIdAvailable(String userId) {
        return !userRepository.existsByUserId(userId);
    }

    public String login(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user.getId();  // 로그인 성공 시 내부 MongoDB용 id 반환
    }
}
