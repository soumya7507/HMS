package com.hms.payload;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.entity.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {
    private String name;
    private Integer no_of_guest;
    private Integer no_of_bedrooms;
    private Integer no_of_bathroom;
    private Integer no_of_beds;
    private Country country;
    private Long country_id;

    private City city;
    private Long city_id;

    private State state;
    private Long state_id;
}
