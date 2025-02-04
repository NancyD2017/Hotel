package com.example.demo.web.controller;

import com.example.demo.entity.Hotel;
import com.example.demo.mapper.HotelMapper;
import com.example.demo.service.HotelService;
import com.example.demo.web.model.request.UpsertHotelRequest;
import com.example.demo.web.model.response.ErrorResponse;
import com.example.demo.web.model.response.HotelListResponse;
import com.example.demo.web.model.response.HotelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    @GetMapping
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<HotelListResponse> findAll() {
        return ResponseEntity.ok(hotelMapper.hotelsToResponse(hotelService.findAll()));
    }

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Optional<Hotel> hotel = hotelService.findById(id);
        return hotel.isPresent()
                ? ResponseEntity.ok().body(hotel)
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Hotel with id " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@RequestBody UpsertHotelRequest request) {
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(
                        hotelService.save(
                                hotelMapper.requestToHotel(request)
                        )
                )
        );
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateHotel(@RequestBody UpsertHotelRequest request,
                                         @PathVariable String id) {
        Hotel updatedHotel = hotelService.update(id, hotelMapper.requestToHotel(request));
        return (updatedHotel == null)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Hotel with id " + id + " not found"))
                : ResponseEntity.ok(hotelMapper.hotelToResponse(updatedHotel));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable String id) {
        hotelService.deleteById(id);
    }

}
