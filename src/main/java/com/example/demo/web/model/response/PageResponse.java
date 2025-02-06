package com.example.demo.web.model.response;

import lombok.Data;

@Data
public class PageResponse {
    private int entityNumber;
    private HotelListResponse hotelListResponse;
}
