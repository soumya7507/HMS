package com.hms.service;

import com.hms.entity.Property;
import com.hms.entity.Rooms;
import com.hms.repository.PropertyRepository;
import com.hms.repository.RoomsRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomsService  {
    private PropertyRepository propertyRepository;
    private RoomsRepository roomsRepository;

    public RoomsService(PropertyRepository propertyRepository, RoomsRepository roomsRepository) {
        this.propertyRepository = propertyRepository;
        this.roomsRepository = roomsRepository;
    }


    public String addRooms(long propertyId, Rooms rooms) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(()->new RuntimeException("Property not found"));
        rooms.setProperty(property);
        roomsRepository.save(rooms);
        return "Room created";
    }
}
