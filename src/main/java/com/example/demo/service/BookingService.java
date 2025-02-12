package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    @Value("${app.kafka.kafkaBooking}")
    private String topicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }
    public Booking save(Booking booking){
        Optional<User> user = userRepository.findById(findUserId());
        Optional<Room> room;
        if (booking.getRoomId() != null && user.isPresent()) {
            room = roomRepository.findById(booking.getRoomId());
            if (room.isPresent()) {
                booking.setRoom(room.get());
            } else throw new NoSuchElementException("Enter correct roomId");
            booking.setUser(user.get());
        } else throw new IllegalArgumentException("You entered incorrect roomId");

        Set<LocalDate> alreadyBookedDates = booking.getRoom().getAlreadyBookedDates();
        Set<LocalDate> desiredDates = new HashSet<>();
        LocalDate firstDate = booking.getMoveInDate();
        while (!firstDate.isAfter(booking.getMoveOutDate())) {
            desiredDates.add(firstDate);
            firstDate = firstDate.plusDays(1);
        }
        if (alreadyBookedDates != null) {
            Set<LocalDate> checkDates = new HashSet<>(alreadyBookedDates);
            checkDates.retainAll(desiredDates);
            if (!checkDates.isEmpty()) throw new IllegalArgumentException("Booking for dates between "
                    + booking.getMoveInDate() + " and " + booking.getMoveOutDate() + " is impossible");
        }
        room.get().addBookedDates(desiredDates);
        try {
            roomService.update(room.get().getId(), room.get());
        } catch (InstanceNotFoundException e){
            throw new RuntimeException("Internal error");
        }
        Booking kbs = bookingRepository.save(booking);
        StatisticsBooking kb = new StatisticsBooking();
        kb.setUserId(booking.getUser().getId());
        kb.setMoveInDate(booking.getMoveInDate());
        kb.setMoveOutDate(booking.getMoveOutDate());
        kb.setId(kbs.getId());
        kafkaTemplate.send(topicName, kb);

        return bookingRepository.save(booking);
    }
    private String findUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.getPrincipal() instanceof UserDetails userDetails &&
                userDetails instanceof com.example.demo.security.UserDetails appUserDetails) {
            return appUserDetails.getId();
        }
        return null;
    }
}
