package com.hms.controller;



import com.hms.entity.AppUser;
import com.hms.entity.Images;
import com.hms.service.ImagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {



    private ImagesService imagesService;

    public ImageController( ImagesService imagesService) {


        this.imagesService = imagesService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //localhost:8080/api/v1/image/upload/file/image/property/1
    public ResponseEntity<?> uploadPropertyPhotos(@RequestParam MultipartFile file,
                                                  @PathVariable String bucketName,
                                                  @PathVariable long propertyId,
                                                  @AuthenticationPrincipal AppUser user
    ) {

//        Property property = propertyRepository.findById(propertyId).get();
//        String imageUrl = bucketService.uploadFile(file, bucketName);
//        Images images=new Images();
//        images.setUrl(imageUrl);
//        images.setProperty(property);
        Images save = imagesService.save(file,bucketName, propertyId, user);
        if(save==null){
            return new ResponseEntity<>("Sorry you are not the owner", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(save, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> listAllPropertyPhotos(@RequestParam long propertyId) {
        List<Images> allImages= imagesService.getAllImages(propertyId);
        if(allImages.isEmpty()){
            return new ResponseEntity<>("No images found", HttpStatus.OK);
        }
        return new ResponseEntity<>(allImages, HttpStatus.OK);
    }
}
