package com.example.demo.web.model.request;

import lombok.Data;

@Data
public class HotelFilterRequest {
    private String hotelId;
    private String name;
    private String header;
    private String city;
    private String address;
    private Double cityCenterDistance;
    private Double rating;
    private Integer numberOfRating;
}
