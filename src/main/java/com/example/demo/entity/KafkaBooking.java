package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "bookingsKafka")
public class KafkaBooking {
    @Id
    private String id;
    private String userId;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
}
