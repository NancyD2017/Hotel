package com.example.demo.repository;

import com.example.demo.entity.KafkaBooking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaBookingRepository extends MongoRepository<KafkaBooking, String> {
}
