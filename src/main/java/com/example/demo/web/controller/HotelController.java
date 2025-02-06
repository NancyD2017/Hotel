package com.example.demo.web.controller;

import com.example.demo.entity.Hotel;
import com.example.demo.filter.HotelFilter;
import com.example.demo.mapper.HotelMapper;
import com.example.demo.mapper.PageMapper;
import com.example.demo.service.HotelService;
import com.example.demo.web.model.request.HotelFilterRequest;
import com.example.demo.web.model.request.PageRequest;
import com.example.demo.web.model.request.UpsertHotelRequest;
import com.example.demo.web.model.request.UpsertRateRequest;
import com.example.demo.web.model.response.ErrorResponse;
import com.example.demo.web.model.response.HotelListResponse;
import com.example.demo.web.model.response.HotelResponse;
import com.example.demo.web.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;
    private final PageMapper pageMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<HotelListResponse> findAll() {
        return ResponseEntity.ok(hotelMapper.hotelsToResponse(hotelService.findAll()));
    }
    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public ResponseEntity<?> findAllPages(@RequestBody PageRequest pageRequest) {
        return (pageRequest.getPageSize() > 0 && pageRequest.getPageNumber() >= 0)
                ? ResponseEntity.ok(
                pageMapper.hotelListResponseToPageResponse(
                hotelMapper.hotelsToResponse(
                        hotelService.findAllPages(pageRequest.getPageNumber(), pageRequest.getPageSize()
                        ))))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Specify pageNumber and pageSize"));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Optional<Hotel> hotel = hotelService.findById(id);
        return hotel.isPresent()
                ? ResponseEntity.ok().body(hotel)
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Hotel with id " + id + " not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
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
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateHotel(@RequestBody UpsertHotelRequest request,
                                         @PathVariable String id) {
        Hotel updatedHotel = hotelService.update(id, hotelMapper.requestToHotel(request));
        return (updatedHotel == null)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Hotel with id " + id + " not found"))
                : ResponseEntity.ok(hotelMapper.hotelToResponse(updatedHotel));
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
        Hotel ratedHotel = hotelService.rate(request.getHotelId(), request.getNewMark());
        if (request.getNewMark() > 5 || request.getNewMark() < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Rate the hotel with mark 1, 2, 3, 4 or 5"));
        return (ratedHotel == null)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Hotel with id " + request.getHotelId() + " not found"))
                : ResponseEntity.ok(hotelMapper.hotelToResponse(ratedHotel));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<HotelListResponse> filterBy(@RequestBody HotelFilterRequest filter) {
        HotelFilter newsFilter = new HotelFilter();

        if (filter.getHotelId() != null) newsFilter.setHotelId(filter.getHotelId());
        if (filter.getName() != null) newsFilter.setName(filter.getName());
        if (filter.getHeader() != null) newsFilter.setHeader(filter.getHeader());
        if (filter.getCity() != null) newsFilter.setCity(filter.getCity());
        if (filter.getAddress() != null) newsFilter.setAddress(filter.getAddress());
        if (filter.getCityCenterDistance() != null) newsFilter.setCityCenterDistance(filter.getCityCenterDistance());
        if (filter.getRating() != null) newsFilter.setRating(filter.getRating());
        if (filter.getNumberOfRating() != null) newsFilter.setNumberOfRating(filter.getNumberOfRating());

        return ResponseEntity.ok(
                hotelMapper.hotelsToResponse(hotelService.filterBy(newsFilter))
        );
    }
}
