package com.rideshare.userservice.service;

import com.rideshare.userservice.dto.RatingRequest;
import com.rideshare.userservice.dto.WalletAddRequest;
import com.rideshare.userservice.model.*;
import com.rideshare.userservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID; // Added UUID import

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserProfileRepository profileRepo;
    private final UserWalletRepository walletRepo;
    private final UserRatingRepository ratingRepo;

    public UserProfile getProfile(String email) {
        return profileRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserProfile updateProfile(String email, UserProfile updated) {
        UserProfile profile = getProfile(email);

        profile.setName(updated.getName());
        profile.setPhone(updated.getPhone());
        profile.setUpdatedAt(LocalDateTime.now());

        return profileRepo.save(profile);
    }

    public UserWallet getWallet(String email) {
        UserProfile profile = getProfile(email);
        // profile.getId() now returns UUID, which matches walletRepo
        return walletRepo.findByUserId(profile.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    public UserWallet addMoney(String email, WalletAddRequest request) {
        UserProfile profile = getProfile(email);

        // profile.getId() returns UUID
        return walletRepo.findByUserId(profile.getId())
                .map(wallet -> {
                    BigDecimal amount = request.getAmount();
                    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("Amount must be positive");
                    }
                    wallet.setBalance(wallet.getBalance().add(amount));
                    wallet.setUpdatedAt(LocalDateTime.now());
                    return walletRepo.save(wallet);
                })
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    public void addRating(String email, RatingRequest request) {
        UserProfile profile = getProfile(email);

        UserRating rating = UserRating.builder()
                .userId(profile.getId()) // Ensure UserRating model uses UUID for userId
                .rideId(request.getRideId())
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        ratingRepo.save(rating);

        int newTotal = profile.getTotalRides() + 1;
        double newRating =
                ((profile.getRating() * profile.getTotalRides())
                        + request.getRating()) / newTotal;

        profile.setRating(newRating);
        profile.setTotalRides(newTotal);

        profileRepo.save(profile);
    }
}