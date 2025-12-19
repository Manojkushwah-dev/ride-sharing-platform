package com.rideshare.userservice.repository;

import com.rideshare.userservice.model.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID; 

public interface UserWalletRepository extends JpaRepository<UserWallet, UUID> {

    Optional<UserWallet> findByUserId(UUID userId); 
}