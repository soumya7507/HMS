package com.hms.repository;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //findByAppUser(AppUser appUser);
    List<Review> findByAppUser(AppUser appUser);
    //existsByAppUserAndPropertyId(AppUser appUser, Long propertyId);
    boolean existsByAppUserAndPropertyId(AppUser appUser, Long propertyId);



    Review findByAppUserAndProperty(AppUser user, Property property);
}