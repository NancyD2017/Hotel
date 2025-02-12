package com.example.demo.web.controller;

import com.example.demo.entity.Hotel;
import com.example.demo.mapper.HotelMapper;
import com.example.demo.service.HotelService;
import com.example.demo.web.model.request.HotelFilterRequest;
import com.example.demo.web.model.request.PageRequest;
import com.example.demo.web.model.request.UpsertHotelRequest;
import com.example.demo.web.model.request.UpsertRateRequest;
import com.example.demo.web.model.response.ErrorResponse;
import com.example.demo.web.model.response.HotelListResponse;
import com.example.demo.web.model.response.HotelPageResponse;
import com.example.demo.web.model.response.HotelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<HotelListResponse> findAll() {
        return ResponseEntity.ok(hotelMapper.hotelsToResponse(hotelService.findAll()));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<HotelListResponse> filterBy(@RequestBody HotelFilterRequest filter) {
        return ResponseEntity.ok(hotelMapper.hotelsToResponse(hotelService.filterBy(filter)));
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> findAllPages(@RequestBody PageRequest pageRequest) {
        try {
            HotelPageResponse list = hotelMapper.hotelListResponseToPageResponse(hotelMapper.hotelsToResponse(
                            hotelService.findAllPages(pageRequest)));
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            Optional<Hotel> hotel = hotelService.findById(id);
            return ResponseEntity.ok().body(hotel);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody UpsertHotelRequest request) {
        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotelService.save(hotelMapper.requestToHotel(request))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateHotel(@RequestBody UpsertHotelRequest request,
                                         @PathVariable String id) {
        try {
            Hotel updatedHotel = hotelService.update(id, hotelMapper.requestToHotel(request));
            return ResponseEntity.ok(hotelMapper.hotelToResponse(updatedHotel));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable String id) {
        hotelService.deleteById(id);
    }

    @PutMapping("/rate")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> rateHotel(@RequestBody UpsertRateRequest request) {
        try {
            Hotel ratedHotel = hotelService.rate(request);
            return ResponseEntity.ok(hotelMapper.hotelToResponse(ratedHotel));
        }
        catch (NoSuchElementException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }
}
