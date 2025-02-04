package com.example.demo.web.model.response;

import com.example.demo.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private String id;
    private String name;
    private String description;
    private String number;
    private Double price;
    private Integer maximumGuestsCapacity;
    private Set<Date> alreadyBookedDates;
    private Hotel hotel;
}
