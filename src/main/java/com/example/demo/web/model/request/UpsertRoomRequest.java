package com.example.demo.web.model.request;

import com.example.demo.entity.Hotel;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UpsertRoomRequest {
    private String name;
    private String description;
    private String number;
    private Double price;
    private Integer maximumGuestsCapacity;
    private Set<Date> alreadyBookedDates;
    private Hotel hotel;
}
