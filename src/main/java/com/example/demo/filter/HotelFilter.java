package com.example.demo.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelFilter {
    private String hotelId;
    private String name;
    private String header;
    private String city;
    private String address;
    private Double cityCenterDistance;
    private Double rating;
    private Integer numberOfRating;
}
