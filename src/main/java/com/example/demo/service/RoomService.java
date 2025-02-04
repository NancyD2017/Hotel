package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    public Optional<Room> findById(String id) throws EntityNotFoundException {
        return roomRepository.findById(id);
    }
    public Room save(Room room){
        return roomRepository.save(room);
    }
    public Room update(String id, Room room){
        Optional<Room> existedRoom = findById(id);
        if (existedRoom.isPresent()) {
            BeanUtils.copyNonNullProperties(room, existedRoom);
            //TODO проверить, что старые даты не исчезают
            return roomRepository.save(room);
        }
        else return null;
    }
    public void deleteById(String id){
        roomRepository.deleteById(id);
    }
}
