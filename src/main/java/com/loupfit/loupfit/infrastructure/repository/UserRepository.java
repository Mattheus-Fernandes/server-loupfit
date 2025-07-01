package com.loupfit.loupfit.infrastructure.repository;

import com.loupfit.loupfit.business.dto.UserDTO;
import com.loupfit.loupfit.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
