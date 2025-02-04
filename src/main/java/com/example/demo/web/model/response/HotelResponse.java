package com.example.demo.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {
    private String id;
    private String name;
    private String header;
    private String city;
    private String address;
    private Double cityCenterDistance;
    private Double rating;
    private Integer reviews;
}
