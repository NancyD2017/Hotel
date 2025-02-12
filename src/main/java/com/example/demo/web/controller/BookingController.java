package com.example.demo.web.controller;

import com.example.demo.entity.Booking;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.service.BookingService;
import com.example.demo.web.model.request.UpsertBookingRequest;
import com.example.demo.web.model.response.BookingListResponse;
import com.example.demo.web.model.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<BookingListResponse> findAll() {
        return ResponseEntity.ok(bookingMapper.bookingsToResponse(bookingService.findAll()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> createBooking(@RequestBody UpsertBookingRequest request) {
        try {
            Booking booking = bookingService.save(bookingMapper.requestToBooking(request));
            return ResponseEntity.ok(bookingMapper.bookingToResponse(booking));
        } catch (IllegalArgumentException | NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }

}
