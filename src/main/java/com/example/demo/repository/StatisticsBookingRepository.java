package com.example.demo.repository;

import com.example.demo.entity.StatisticsBooking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsBookingRepository extends MongoRepository<StatisticsBooking, String> {
}
