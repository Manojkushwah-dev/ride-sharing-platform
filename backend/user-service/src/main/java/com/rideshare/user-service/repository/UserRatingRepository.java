package com.rideshare.userservice.repository;

import com.rideshare.userservice.model.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
}
