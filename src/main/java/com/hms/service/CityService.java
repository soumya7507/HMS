package com.hms.service;

import com.hms.entity.City;
import com.hms.entity.Property;
import com.hms.payload.CityDto;
import com.hms.repository.CityRepository;
import com.hms.repository.CountryRepository;
import com.hms.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PropertyRepository propertyRepository;
    public City addCity(City city){
        City save = cityRepository.save(city);
        return save;
    }

    public void addCity(CityDto cityDto) {

        City city=new City();
        city.setName(city.getName());
        cityRepository.save(city);
    }


    public List<City> getAllCity() {
        List<City> all = cityRepository.findAll();
        return all;
    }
    @Transactional

    public void deleteCityById(long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new EntityNotFoundException("City Not Found"));
        List<Property> property=propertyRepository.findByCity(city);
        for(Property p:property){
            p.setCity(null);
            propertyRepository.save(p);
        }
        cityRepository.deleteById(cityId);

    }


    public boolean updateCityById(long cityId, CityDto cityDto) {
        Optional<City> byId = cityRepository.findById(cityId);
        if (byId.isPresent()) {
            City city = byId.get();
            city.setName(cityDto.getName());
            cityRepository.save(city);
            return true;
        }
        return false;
    }

    public void addNewCity(CityDto cityDto) {


        City city=new City();
        city.setName(cityDto.getName());
        cityRepository.save(city);
    }

}

