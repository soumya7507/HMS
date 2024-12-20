package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import com.hms.repository.AppUserRepository;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;
    private AppUserRepository appUserRepository;
    private BucketService bucketService;
    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository, AppUserRepository appUserRepository, BucketService bucketService) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
        this.appUserRepository = appUserRepository;
        this.bucketService = bucketService;
    }



    public Review addReview(Review review, long propertyId, AppUser user, MultipartFile file, String bucketName) {
        Property property = propertyRepository.findById(propertyId).get();
        boolean b = reviewRepository.existsByAppUserAndPropertyId(user, propertyId);
        if(b==false){
            review.setProperty(property);
            review.setAppUser(user);
            String imageUrl = bucketService.uploadFile(file, bucketName);
            review.setImageUrl(imageUrl);
            Review save = reviewRepository.save(review);
            return save;
        }
        return null;
    }

    public String deleteReview(long reviewId,AppUser user) {
        AppUser appUser = appUserRepository.findById(user.getId()).get();
        Review review = reviewRepository.findById(reviewId).get();
        if(review==null){
            return "No review exists";
        }
        if (review.getAppUser().getId() != appUser.getId()){
            return "You cannot delete other users review";
        }
        reviewRepository.delete(review);
        return "Review deleted";

    }

    public String updateUserReview(Review review, AppUser user, long propertyId) {


        Property property = propertyRepository.findById(propertyId).get();
        Review save = reviewRepository.findByAppUserAndProperty(user, property);
        if(save==null){
            return "No review exists";
        }
        if(save.getRating()==review.getRating() && save.getDescription().equals(review.getDescription())){
            return "Review is same as previous review";
        }
        save.setRating(review.getRating());
        save.setDescription(review.getDescription());
        reviewRepository.save(save);
        return "Updated user review";
    }
}

