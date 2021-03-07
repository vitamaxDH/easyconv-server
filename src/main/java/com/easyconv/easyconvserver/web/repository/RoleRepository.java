package com.easyconv.easyconvserver.web.repository;

import com.easyconv.easyconvserver.web.model.ERole;
import com.easyconv.easyconvserver.web.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole roleUser);
}
