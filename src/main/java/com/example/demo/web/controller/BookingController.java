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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @GetMapping
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<BookingListResponse> findAll() {
        return ResponseEntity.ok(bookingMapper.bookingsToResponse(bookingService.findAll()));
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody UpsertBookingRequest request) {
        Booking booking = bookingService.save(bookingMapper.requestToBooking(request));
        return (booking == null) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(
                                "Booking for dates between " + request.getMoveInDate() +
                                        " and " + request.getMoveOutDate() + " is impossible"))
                : ResponseEntity.ok(bookingMapper.bookingToResponse(booking));
    }

}
