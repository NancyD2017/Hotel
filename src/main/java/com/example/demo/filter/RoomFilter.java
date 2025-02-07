package com.example.demo.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RoomFilter {
    private String roomId;
    private String header;
    private Double minPrice;
    private Double maxPrice;
    private Integer maximumGuestsCapacity;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private String hotelId;
}
