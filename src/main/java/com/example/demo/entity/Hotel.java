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
    @Field(name = "city_center_distance")
    private Double cityCenterDistance;
    @Builder.Default
    private Double rating = 0.0;
    @Builder.Default
    private Integer numberOfRating = 0;
}