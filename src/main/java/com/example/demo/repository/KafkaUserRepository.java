package com.example.demo.repository;

import com.example.demo.entity.KafkaUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaUserRepository extends MongoRepository<KafkaUser, String> {
}
