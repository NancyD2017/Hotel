package com.example.demo.service;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import com.example.demo.filter.RoomFilter;
import com.example.demo.filter.RoomSpecification;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final MongoTemplate mongoTemplate;
    public Optional<Room> findById(String id) throws TypeNotPresentException {
        return roomRepository.findById(id);
    }
    public Room save(Room room){
        if (room.getHotelId() != null) {
            Optional<Hotel> hotel = hotelRepository.findById(room.getHotelId());
            if (hotel.isPresent()) {
                room.setHotel(hotel.get());
                return roomRepository.save(room);
            }
        }
        return null;
    }
    public Room update(String id, Room room){
        Optional<Room> existedRoom = findById(id);
        if (existedRoom.isPresent()) {
            Room existingRoom = existedRoom.get();
            BeanUtils.copyNonNullProperties(room, existingRoom);
            return roomRepository.save(existingRoom);
        }
        else return null;
    }
    public void deleteById(String id){
        roomRepository.deleteById(id);
    }
    public List<Room> filterBy(RoomFilter filter, int pageSize, int pageNumber){
        Query query = RoomSpecification.withFilter(filter);
        query.skip((long) pageNumber * pageSize).limit(pageSize);
        return mongoTemplate.find(query, Room.class);
    }
}
