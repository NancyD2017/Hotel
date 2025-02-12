package com.example.demo.web.controller;

import com.example.demo.entity.Room;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.service.RoomService;
import com.example.demo.web.model.request.RoomFilterRequest;
import com.example.demo.web.model.request.UpsertRoomRequest;
import com.example.demo.web.model.response.ErrorResponse;
import com.example.demo.web.model.response.RoomListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.NoSuchElementException;
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
        try {
            Optional<Room> room = roomService.findById(id);
            return ResponseEntity.ok().body(room);
        } catch (InstanceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> filterBy(@RequestBody RoomFilterRequest filter) {
        try {
            RoomListResponse list =
                    roomMapper.roomsToResponse(roomService.filterBy(filter, filter.getPageSize(), filter.getPageNumber()));
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Specify pageSize and pageNumber"));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createRoom(@RequestBody UpsertRoomRequest request) {
        try {
            Room createdRoom = roomService.save(roomMapper.requestToRoom(request));
            return ResponseEntity.ok(roomMapper.roomToResponse(createdRoom));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateRoom(@RequestBody UpsertRoomRequest request, @PathVariable String id) {
        try {
            Room updatedRoom = roomService.update(id, roomMapper.requestToRoom(request));
            return ResponseEntity.ok(roomMapper.roomToResponse(updatedRoom));
        } catch (InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable String id) {
        roomService.deleteById(id);
    }

}
