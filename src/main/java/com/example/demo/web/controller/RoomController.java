package com.example.demo.web.controller;

import com.example.demo.entity.Room;
import com.example.demo.filter.RoomFilter;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.service.RoomService;
import com.example.demo.web.model.request.RoomFilterRequest;
import com.example.demo.web.model.request.UpsertRoomRequest;
import com.example.demo.web.model.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Optional<Room> room = roomService.findById(id);
        return room.isPresent()
                ? ResponseEntity.ok().body(room)
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Room with id " + id + " not found"));
    }
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> filterBy(@RequestBody RoomFilterRequest filter) {
        Integer pn = filter.getPageNumber();
        Integer ps = filter.getPageSize();

        if ((pn == null ^ ps == null) || (ps != null && ps <= 0) || (pn != null && pn < 0))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Specify both pageNumber and pageSize"));
        if (filter.getMoveInDate() == null ^ filter.getMoveOutDate() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Specify both moveInDate and moveOutDate"));
        if (filter.getMoveInDate().isAfter(filter.getMoveOutDate()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Specify moveInDate before moveOutDate"));
        else if (pn == null && ps == null) {
            filter.setPageSize(0);
            filter.setPageNumber(100);
        }

        RoomFilter roomFilter = new RoomFilter();

        if (filter.getHotelId() != null) roomFilter.setHotelId(filter.getHotelId());
        if (filter.getRoomId() != null) roomFilter.setRoomId(filter.getRoomId());
        if (filter.getHeader() != null) roomFilter.setHeader(filter.getHeader());
        if (filter.getMaxPrice() != null) roomFilter.setMaxPrice(filter.getMaxPrice());
        if (filter.getMinPrice() != null) roomFilter.setMinPrice(filter.getMinPrice());
        if (filter.getMoveInDate() != null) roomFilter.setMoveInDate(filter.getMoveInDate());
        if (filter.getMoveOutDate() != null) roomFilter.setMoveOutDate(filter.getMoveOutDate());
        if (filter.getMaximumGuestsCapacity() != null) roomFilter.setMaximumGuestsCapacity(filter.getMaximumGuestsCapacity());

        return ResponseEntity.ok(
                roomMapper.roomsToResponse(roomService.filterBy(roomFilter, filter.getPageSize(), filter.getPageNumber()))
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createRoom(@RequestBody UpsertRoomRequest request) {
        Room createdRoom = roomService.save(roomMapper.requestToRoom(request));
        return (createdRoom == null)
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Bad data. Try to change hotelId"))
                : ResponseEntity.ok(roomMapper.roomToResponse(createdRoom));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateRoom(@RequestBody UpsertRoomRequest request,
                                                         @PathVariable String id) {
        Room updatedRoom = roomService.update(id, roomMapper.requestToRoom(request));
        return (updatedRoom == null)
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Room with id " + id + " not found"))
                : ResponseEntity.ok(roomMapper.roomToResponse(updatedRoom));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable String id) {
        roomService.deleteById(id);
    }

}
