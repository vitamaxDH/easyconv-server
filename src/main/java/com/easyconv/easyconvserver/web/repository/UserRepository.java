package com.easyconv.easyconvserver.web.repository;

import com.easyconv.easyconvserver.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
