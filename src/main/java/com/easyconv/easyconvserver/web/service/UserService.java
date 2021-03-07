package com.easyconv.easyconvserver.web.service;

import com.easyconv.easyconvserver.web.model.User;
import com.easyconv.easyconvserver.web.payload.request.SignupRequest;
import com.easyconv.easyconvserver.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public User mapToEntity(SignupRequest signupRequest){
        return User.builder()
                    .username(signupRequest.getUsername())
                    .email(signupRequest.getEmail())
                    .password(encoder.encode(signupRequest.getPassword()))
                    .build();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public boolean existsUser(SignupRequest signupRequest){
        return (existsByUsername(signupRequest.getUsername()) || existsByEmail(signupRequest.getEmail()));
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
