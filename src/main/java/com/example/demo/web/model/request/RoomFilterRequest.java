package com.example.demo.web.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomFilterRequest {
    private String roomId;
    private String header;
    private Double minPrice;
    private Double maxPrice;
    private Integer maximumGuestsCapacity;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private String hotelId;

    private Integer pageSize;
    private Integer pageNumber;
}
