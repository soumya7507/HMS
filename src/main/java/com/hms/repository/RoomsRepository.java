package com.hms.repository;

import com.hms.entity.Property;
import com.hms.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
    @Query("SELECT r FROM Rooms r WHERE r.property = :property AND r.type=:type and r.date between :checkInDate and :checkOutDate")
    List<Rooms> findAvailableRooms(@Param("property") Property property,
                                   @Param("type") String type,
                                   @Param("checkInDate") LocalDate checkInDate,
                                   @Param("checkOutDate") LocalDate checkOut);


    //Optional<Rooms> findByDate(LocalDate checkOutDate);
}