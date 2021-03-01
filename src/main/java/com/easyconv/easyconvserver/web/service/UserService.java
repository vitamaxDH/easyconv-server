package com.easyconv.easyconvserver.web.service;

import com.easyconv.easyconvserver.web.model.RoleType;
import com.easyconv.easyconvserver.web.model.User;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(User user){
        user.setRole(RoleType.ROLE_USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

}
