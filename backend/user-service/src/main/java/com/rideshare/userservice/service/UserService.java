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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserProfileRepository profileRepo;
    private final UserWalletRepository walletRepo;
    private final UserRatingRepository ratingRepo;

    public UserProfile createProfile(String name, String email, String phone, String role) {
        if (profileRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserProfile profile = UserProfile.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .role(role)
                .rating(5.0)
                .totalRides(0)
                .createdAt(LocalDateTime.now())   // OK (assuming profile doesn’t use @CreationTimestamp)
                .updatedAt(LocalDateTime.now())
                .build();

        UserProfile saved = profileRepo.save(profile);

        // ✅ Create wallet WITHOUT timestamps (Hibernate handles them)
        UserWallet wallet = UserWallet.builder()
                .userId(saved.getId())
                .balance(BigDecimal.ZERO)
                .build();

        walletRepo.save(wallet);

        return saved;
    }

    public UserProfile getProfile(String email) {
        return profileRepo.findByEmail(email)
                .orElseGet(() -> {
                    UserProfile defaultProfile = UserProfile.builder()
                            .email(email)
                            .name("User")
                            .role("USER")
                            .rating(5.0)
                            .totalRides(0)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();

                    UserProfile saved = profileRepo.save(defaultProfile);

                    // ✅ Wallet creation without timestamps
                    UserWallet wallet = UserWallet.builder()
                            .userId(saved.getId())
                            .balance(BigDecimal.ZERO)
                            .build();

                    walletRepo.save(wallet);

                    return saved;
                });
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

        return walletRepo.findByUserId(profile.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    public UserWallet addMoney(String email, WalletAddRequest request) {
        UserProfile profile = getProfile(email);

        return walletRepo.findByUserId(profile.getId())
                .map(wallet -> {
                    BigDecimal amount = request.getAmount();
                    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("Amount must be positive");
                    }

                    wallet.setBalance(wallet.getBalance().add(amount));
                    // ❌ REMOVE manual updatedAt — Hibernate auto-updates it
                    return walletRepo.save(wallet);
                })
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    public void addRating(String email, RatingRequest request) {
        UserProfile profile = getProfile(email);

        UserRating rating = UserRating.builder()
                .userId(profile.getId()) // ensure UserRating uses UUID
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
