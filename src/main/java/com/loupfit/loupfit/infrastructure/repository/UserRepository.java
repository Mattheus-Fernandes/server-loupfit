package com.loupfit.loupfit.infrastructure.repository;

import com.loupfit.loupfit.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
}
