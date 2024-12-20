package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.entity.Images;
import com.hms.entity.Property;
import com.hms.exception.ResourceNotFound;
import com.hms.repository.AppUserRepository;
import com.hms.repository.ImagesRepository;
import com.hms.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImagesService {
    private ImagesRepository imagesRepository;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;
    private AppUserRepository appUserRepository;

    public ImagesService(ImagesRepository imagesRepository, PropertyRepository propertyRepository, BucketService bucketService, AppUserRepository appUserRepository) {
        this.imagesRepository = imagesRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
        this.appUserRepository = appUserRepository;
    }


    public List<Images> getAllImages(long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(()-> new ResourceNotFound("Property not found"));
        List<Images> byProperty = imagesRepository.findByProperty(property);
        return byProperty;
    }



    public Images save(MultipartFile file, String bucketName, long propertyId, AppUser user) {

        Property property = propertyRepository.findById(propertyId).get();
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Images images=new Images();
        images.setUrl(imageUrl);
        images.setProperty(property);
        Images save = imagesRepository.save(images);
        return save;
    }


}

