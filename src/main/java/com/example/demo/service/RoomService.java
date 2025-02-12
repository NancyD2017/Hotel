package com.example.demo.service;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import com.example.demo.filter.RoomFilter;
import com.example.demo.filter.RoomSpecification;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.utils.BeanUtils;
import com.example.demo.web.model.request.RoomFilterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final MongoTemplate mongoTemplate;
    public Optional<Room> findById(String id) throws InstanceNotFoundException {
        Optional<Room> room = roomRepository.findById(id);
        if (!room.isPresent()) throw new InstanceNotFoundException("Room with id " + id + " not found");
        else return room;
    }
    public Room save(Room room){
        if (room.getHotelId() != null) {
            Optional<Hotel> hotel = hotelRepository.findById(room.getHotelId());
            if (hotel.isPresent()) {
                room.setHotel(hotel.get());
                return roomRepository.save(room);
            } else throw new NoSuchElementException("Bad data. Try to change hotelId");
        }
        throw new NoSuchElementException("Bad data. Try to change hotelId");
    }
    public Room update(String id, Room room) throws InstanceNotFoundException {
        try {
        Optional<Room> existedRoom = findById(id);
        Room existingRoom = existedRoom.get();
        BeanUtils.copyNonNullProperties(room, existingRoom);
        return roomRepository.save(existingRoom);
        } catch (InstanceNotFoundException e){
            throw new InstanceNotFoundException(e.getMessage());
        }
    }
    public void deleteById(String id){
        roomRepository.deleteById(id);
    }
    public List<Room> filterBy(RoomFilterRequest filter, int pageSize, int pageNumber){
        Integer pn = filter.getPageNumber();
        Integer ps = filter.getPageSize();

        if ((pn == null ^ ps == null) || (ps != null && ps <= 0) || (pn != null && pn < 0))
            throw new IllegalArgumentException("Specify both pageNumber and pageSize");
        if (filter.getMoveInDate() == null ^ filter.getMoveOutDate() == null)
            throw new IllegalArgumentException("Specify both moveInDate and moveOutDate");
        if (filter.getMoveInDate() != null && filter.getMoveOutDate() != null && filter.getMoveInDate().isAfter(filter.getMoveOutDate()))
            throw new IllegalArgumentException("Specify moveInDate before moveOutDate");
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

        Query query = RoomSpecification.withFilter(roomFilter);
        query.skip((long) pageNumber * pageSize).limit(pageSize);
        return mongoTemplate.find(query, Room.class);
    }
}
