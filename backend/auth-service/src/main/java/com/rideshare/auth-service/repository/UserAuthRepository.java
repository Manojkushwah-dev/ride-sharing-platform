package com.rideshare.authservice.repository;

import com.rideshare.authservice.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByEmail(String email);

    boolean existsByEmail(String email);
}
