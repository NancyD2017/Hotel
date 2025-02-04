package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }
    public Booking save(Booking booking){
        Set<LocalDate> alreadyBookedDates = booking.getRoom().getAlreadyBookedDates();
        Set<LocalDate> desiredDates = new HashSet<>();
        LocalDate firstDate = booking.getMoveInDate();
        while (!firstDate.isAfter(booking.getMoveOutDate())) {
            desiredDates.add(firstDate);
            firstDate = firstDate.plusDays(1);
        }
        if (alreadyBookedDates.contains(desiredDates)) return null;
        return bookingRepository.save(booking);
    }
    //TODO проверить правильное бронирование
}
