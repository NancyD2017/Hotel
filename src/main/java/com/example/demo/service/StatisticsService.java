package com.example.demo.service;

import com.example.demo.entity.StatisticsBooking;
import com.example.demo.entity.StatisticsUser;
import com.example.demo.repository.StatisticsBookingRepository;
import com.example.demo.repository.StatisticsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsUserRepository userRepository;
    private final StatisticsBookingRepository bookingRepository;
    public void post(StatisticsUser event){
        userRepository.save(event);
    }
    public void post(StatisticsBooking event){
        bookingRepository.save(event);
    }
    public List<Object> getAll(){
        List<Object> result = new ArrayList<>();
        result.addAll(userRepository.findAll());
        result.addAll(bookingRepository.findAll());
        return result;
    }
    public File downloadCSV(){
        File file = new File("Statistics.csv");
        try {
            PrintWriter pw = new PrintWriter(file);
            getAll().forEach(pw::println);
            pw.flush();
        } catch (Exception e) {
            return null;
        }
        return file;
    }
}
