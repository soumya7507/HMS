package com.hms.controller;


import com.hms.entity.AppUser;
import com.hms.entity.Review;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import com.hms.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController

@RequestMapping("/api/v1/review")

public class ReviewController {
    private PropertyRepository propertyRepository;
    private ReviewRepository reviewRepository;
    private ReviewService reviewService;

    public ReviewController(PropertyRepository propertyRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
    }
    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> write(
            @RequestParam MultipartFile file,
            @PathVariable String bucketName,
            @RequestBody Review review,
            @PathVariable long propertyId,
            @AuthenticationPrincipal AppUser user
    ){

        Review save = reviewService.addReview(review, propertyId, user, file, bucketName);
        if (save==null){
            return new ResponseEntity<>("Already review exists by the user",HttpStatus.OK);
        }
        return new ResponseEntity<>(save, HttpStatus.OK);

    }

    @GetMapping("/user/review")
    public ResponseEntity<List<Review>> getUserReview(
            @AuthenticationPrincipal AppUser appUser

    ){
        List<Review> reviews=reviewRepository.findByAppUser(appUser);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }
    //localhost:8080/api/v1/review/update?propertyId
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUsersReview(

            @RequestBody Review review,
            @AuthenticationPrincipal AppUser user,
            @PathVariable("id") long propertyId
    ) {

        String s = reviewService.updateUserReview(review, user, propertyId);

        return  new ResponseEntity<>(s,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable("id") long reviewId,
                               @AuthenticationPrincipal AppUser user
    ){
        String status=reviewService.deleteReview(reviewId,user);
        return status;
    }
}
