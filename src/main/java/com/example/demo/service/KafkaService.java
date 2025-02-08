package com.example.demo.service;

import com.example.demo.entity.KafkaBooking;
import com.example.demo.entity.KafkaUser;
import com.example.demo.repository.KafkaBookingRepository;
import com.example.demo.repository.KafkaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaUserRepository userRepository;
    private final KafkaBookingRepository bookingRepository;
    public void post(KafkaUser event){
        userRepository.save(event);
    }
    public void post(KafkaBooking event){
        bookingRepository.save(event);
    }
    public List<Object> getAll(){
        List<Object> result = new ArrayList<>();
        result.addAll(userRepository.findAll());
        result.addAll(bookingRepository.findAll());
        return result;
    }
}
