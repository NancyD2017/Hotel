package com.example.demo.web.controller;

import com.example.demo.entity.Room;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.service.RoomService;
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
