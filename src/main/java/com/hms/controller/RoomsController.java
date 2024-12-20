package com.hms.controller;

import com.hms.entity.Rooms;
import com.hms.service.RoomsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomsController {
    private RoomsService roomsService;

    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }


    @PostMapping("/{id}")
    //
    public String addRoomsDetails(
            @PathVariable("id") long propertyId,
            @RequestBody Rooms rooms
    ){
        String s = roomsService.addRooms(propertyId,rooms);
        return s;
    }
}
