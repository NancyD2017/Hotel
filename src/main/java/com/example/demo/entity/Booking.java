package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "booking")
public class Booking {
    @Id
    private String id;
    @Field(name = "move_in_date")
    private LocalDate moveInDate;
    @Field(name = "move_out_date")
    private LocalDate moveOutDate;
    private Room room;
    private User user;
}