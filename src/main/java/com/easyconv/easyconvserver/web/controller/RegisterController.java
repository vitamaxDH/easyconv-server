package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.web.model.User;
import com.easyconv.easyconvserver.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RegisterController extends BaseController{

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(HttpServletRequest request, HttpServletResponse response, @RequestBody User user){
        log.info("register :: user {}", user);

        userService.save(user);

        return ResponseEntity.ok(user);
    }
}
