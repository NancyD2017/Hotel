package com.example.demo.repository;

import com.example.demo.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {
    Page<Hotel> findAll(Pageable pageable);
}
