package com.example.demo.service;

import com.example.demo.entity.Hotel;
import com.example.demo.repository.HotelRepository;
import com.example.demo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {
    private final HotelRepository hotelRepository;
    public List<Hotel> findAll(){
        return hotelRepository.findAll();
    }

    public Optional<Hotel> findById(String id) throws TypeNotPresentException {
        return hotelRepository.findById(id);
    }

    public Hotel save(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    public Hotel update(String id, Hotel hotel) {
        Optional<Hotel> existedHotel = findById(id);
        if (existedHotel.isPresent()) {
            Hotel existingHotel = existedHotel.get();
            BeanUtils.copyNonNullProperties(hotel, existingHotel);
            return hotelRepository.save(existingHotel);
        }
        return null;
    }

    public void deleteById(String id){
        hotelRepository.deleteById(id);
    }
}
