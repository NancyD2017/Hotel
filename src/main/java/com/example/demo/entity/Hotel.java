package com.example.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "hotel")
public class Hotel {
    @Id
    private String id;
    private String name;
    private String header;
    private String city;
    private String address;
    @Field(name = "city_center_distance Привет Насте")
    private Double cityCenterDistance;
    private Double rating;
    private Integer reviews;
}