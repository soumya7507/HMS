package com.hms.repository;

import com.hms.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
    List<Bookings> findByName(String name);
}