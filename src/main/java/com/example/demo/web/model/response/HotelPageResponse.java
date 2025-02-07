package com.example.demo.web.model.response;

import lombok.Data;

@Data
public class HotelPageResponse {
    private int entityNumber;
    private HotelListResponse hotelListResponse;
}
