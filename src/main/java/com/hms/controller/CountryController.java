package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Country;
import com.hms.payload.CountryDto;
import com.hms.repository.CountryRepository;
import com.hms.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    private CountryService countryService;
    private  CountryRepository countryRepository;


    public CountryController(CountryService countryService,
                             CountryRepository countryRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<?> createCountry(
            @RequestBody CountryDto countryDto

    ) {
        Optional<Country> byName = countryRepository.findByName(countryDto.getName());
        if(byName.isPresent()){
            return new ResponseEntity<>("Already present ",HttpStatus.OK);
        }
        countryService.addCountry(countryDto);
        return new ResponseEntity<>("Added successfully",HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Country> all = countryRepository.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    //localhost:8080/api/v1/country
    @DeleteMapping("/{id}")
    public String deleteCountry(@PathVariable("id") long countryId){
        Country country = countryService.findById(countryId);

        if (country != null) {
            // Delete the country (if no cascading delete is set on properties, properties will not be deleted)
            countryService.deleteCountryById(countryId);
            return "Country Deleted, Properties Retained";
        } else {
            return "Country not found";
        }
    }

    @PutMapping("/{id}")
    public String updateCountry(@PathVariable("id") long countryId,
                                @RequestBody CountryDto countryDto){
        boolean b = countryService.updateCountryById(countryId, countryDto);
        if(b==true){
            return "Updated";
        }
        return "Id not found";
    }

    @PostMapping("/demo")
    public AppUser demo(
            @AuthenticationPrincipal AppUser user
    ){
        return user;
    }
}

