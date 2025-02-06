package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "room")
public class Room {
    @Id
    private String id;
    private String name;
    private String description;
    private String number;
    private Double price;
    @Field(name = "maximum_guests_capacity")
    private Integer maximumGuestsCapacity;
    @Field(name = "already_booked_dates")
    @Builder.Default
    private Set<LocalDate> alreadyBookedDates =  new HashSet<>();
    private String hotelId;
    private Hotel hotel;

    public void addBookedDates(Set<LocalDate> dates){
        alreadyBookedDates.addAll(dates);
    }
}