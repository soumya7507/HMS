package com.hms.repository;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByName(String name);
    @Query(
            "select p from Property p JOIN  p.city c JOIN p.country co JOIN  " +
                    "p.state  s where c.name=:name or co.name=:name or s.name=:name ")
    List<Property> searchHotel(@Param("name") String name

    );

    List<Property> findByCountry(Country country);

    List<Property> findByCity(City city);
}