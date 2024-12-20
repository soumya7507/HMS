package com.hms.controller;

import com.hms.entity.City;
import com.hms.payload.CityDto;
import com.hms.repository.CityRepository;
import com.hms.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {
    private CityService cityService;
    private CityRepository cityRepository;

    public CityController(CityService cityService, CityRepository cityRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
    }

    @PostMapping("/addCity")
    public ResponseEntity<?> addCityDetails(
            @RequestBody CityDto cityDto
    ){

        Optional<City> byName = cityRepository.findByName(cityDto.getName());
        if(byName.isPresent()){
            return new ResponseEntity<>("City already exits", HttpStatus.OK);
        }

//            City city=new City();
//
//            city.setName(cityDto.getName());
//            cityRepository.save(city);
//
        else {

            cityService.addNewCity(cityDto);
            return new ResponseEntity<>("City added successfully", HttpStatus.CREATED);
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllCity(){
        List<City> all = cityRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);

    }
    //Localhost:8080/api/v1/city/deleteCityById/1
    @DeleteMapping("/deleteCityById/{id}")
    public String deleteCity(@PathVariable("id") long cityId){
        cityService.deleteCityById(cityId);
        return "Deleted";
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<City> all = cityService.getAllCity();
        return new ResponseEntity<>(all,HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCity(@PathVariable("id") long cityId,
                                        @RequestBody CityDto cityDto){
        boolean b = cityService.updateCityById(cityId, cityDto);
        if(b==true){

            return new ResponseEntity<>("Updated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not found",HttpStatus.OK);
    }

}
