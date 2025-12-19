package com.rideshare.userservice.controller; // Ensure this matches your folder name

import com.rideshare.userservice.dto.*;
import com.rideshare.userservice.model.*;
import com.rideshare.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users") // Standardized path
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserProfile getProfile(@RequestHeader("X-USER-EMAIL") String email) {
        return userService.getProfile(email);
    }

    @PutMapping("/me")
    public UserProfile updateProfile(
            @RequestHeader("X-USER-EMAIL") String email,
            @RequestBody UserProfile profile) {
        return userService.updateProfile(email, profile);
    }

    @GetMapping("/me/wallet")
    public UserWallet getWallet(@RequestHeader("X-USER-EMAIL") String email) {
        return userService.getWallet(email);
    }

    @PostMapping("/me/wallet/add")
    public UserWallet addMoney(
            @RequestHeader("X-USER-EMAIL") String email,
            @RequestBody WalletAddRequest request) {
        return userService.addMoney(email, request);
    }

    @PostMapping("/me/ratings")
    public void addRating(
            @RequestHeader("X-USER-EMAIL") String email,
            @RequestBody RatingRequest request) {
        userService.addRating(email, request);
    }
}