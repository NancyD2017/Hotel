package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
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
            } else return null;
            booking.setUser(user.get());
        } else return null;
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
            if (!checkDates.isEmpty()) return null;
        }
        room.get().addBookedDates(desiredDates);
        roomService.update(room.get().getId(), room.get());
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
