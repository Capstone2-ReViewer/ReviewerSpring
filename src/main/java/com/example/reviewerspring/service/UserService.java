package com.example.reviewerspring.service;

import com.example.reviewerspring.domain.*;
import com.example.reviewerspring.dto.UserSignupRequest;
import com.example.reviewerspring.dto.UserUpdateRequest;
import com.example.reviewerspring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserTagPreferredRepository preferredRepository;
    private final UserTagRelateRepository relateRepository;
    private final UserWishlistRepository wishlistRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    /*private String getTagNameById(Integer tagId) {
        return switch (tagId) {
            case 1 -> "액션";
            case 2 -> "어드벤처";
            case 3 -> "RPG";
            case 4 -> "시뮬레이션";
            case 5 -> "스포츠";
            case 6 -> "전략";
            case 7 -> "캐주얼";
            case 8 -> "공포";
            case 9 -> "퍼즐";
            case 10 -> "레이싱";
            default -> "알 수 없음";
        };
    }

    private List<Integer> parseTagIds(String tagStr) {
        if (tagStr == null || tagStr.trim().isEmpty()) return new ArrayList<>();

        return Arrays.stream(tagStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<UserSignupRequest.TagInfo> convertToTagInfoList(String tagStr) {
        List<Integer> tagIds = parseTagIds(tagStr);
        List<UserSignupRequest.TagInfo> tagInfos = new ArrayList<>();

        for (Integer tagId : tagIds) {
            String tagName = getTagNameById(tagId);
            tagInfos.add(new UserSignupRequest.TagInfo(tagId, tagName));
        }

        return tagInfos;
    }

    private List<UserUpdateRequest.TagInfo> convertToTagInfoListForUpdate(String tagStr) {
        List<Integer> tagIds = parseTagIds(tagStr);
        List<UserUpdateRequest.TagInfo> tagInfos = new ArrayList<>();

        for (Integer tagId : tagIds) {
            String tagName = getTagNameById(tagId);
            tagInfos.add(new UserUpdateRequest.TagInfo(tagId, tagName));
        }

        return tagInfos;
    }*/

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

        List<UserSignupRequest.TagInfo> preferredTags = request.getPreferredTags();
        List<UserSignupRequest.TagInfo> dislikedTags = request.getDislikedTags();

        for (UserSignupRequest.TagInfo tag : preferredTags) {
            UserTagPreferred preferred = new UserTagPreferred();
            preferred.setUserId(userId);
            preferred.setTagId(tag.getTagId());
            preferred.setTagName(tag.getTagName());
            preferred.setPreferred(true);
            preferredRepository.save(preferred);
        }

        for (UserSignupRequest.TagInfo tag : dislikedTags) {
            UserTagPreferred disliked = new UserTagPreferred();
            disliked.setUserId(userId);
            disliked.setTagId(tag.getTagId());
            disliked.setTagName(tag.getTagName());
            disliked.setPreferred(false);
            preferredRepository.save(disliked);
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

    public void updateUser(UserUpdateRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자 정보 업데이트
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            if (!request.getPassword().equals(request.getPasswordCheck())) {
                throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setAge(request.getAge());
        user.setGender(request.getGender());

        userRepository.save(user);

        preferredRepository.deleteByUserId(user.getId());

        List<UserUpdateRequest.TagInfo> preferredTags = request.getPreferredTags();
        List<UserUpdateRequest.TagInfo> dislikedTags = request.getDislikedTags();

        for (UserUpdateRequest.TagInfo tag : preferredTags) {
            UserTagPreferred preferred = new UserTagPreferred();
            preferred.setUserId(user.getId());
            preferred.setTagId(tag.getTagId());
            preferred.setTagName(tag.getTagName());
            preferred.setPreferred(true);
            preferredRepository.save(preferred);
        }

        for (UserUpdateRequest.TagInfo tag : dislikedTags) {
            UserTagPreferred disliked = new UserTagPreferred();
            disliked.setUserId(user.getId());
            disliked.setTagId(tag.getTagId());
            disliked.setTagName(tag.getTagName());
            disliked.setPreferred(false);
            preferredRepository.save(disliked);
        }
    }

    public void deleteUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 관련 문서 먼저 삭제
        preferredRepository.deleteByUserId(user.getId());
        relateRepository.deleteByUserId(user.getId());
        wishlistRepository.deleteByUserId(user.getId());

        // 유저 삭제
        userRepository.delete(user);
    }
}  
