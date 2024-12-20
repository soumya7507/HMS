package com.hms.payload;

import com.hms.entity.Property;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDto {

    private String name;
    private String emailId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Property property;
    private Long property_id;
}
