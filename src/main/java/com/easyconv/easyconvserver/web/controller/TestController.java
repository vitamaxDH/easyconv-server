package com.easyconv.easyconvserver.web.controller;

import com.easyconv.easyconvserver.config.Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    @GetMapping("/prop/{key}")
    public ResponseEntity<?> getPropertyValue(@PathVariable String key){

        String value = Config.getProperty(key);

        return ResponseEntity.ok(value);
    }
}
