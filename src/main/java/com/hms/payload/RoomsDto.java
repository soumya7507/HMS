package com.hms.payload;

import com.hms.entity.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomsDto {
    private String type;
    private int count;

    private Property property;
    private Long property_id;
}
