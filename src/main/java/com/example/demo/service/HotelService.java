package com.example.demo.service;

import com.example.demo.entity.Hotel;
import com.example.demo.filter.HotelFilter;
import com.example.demo.filter.HotelSpecification;
import com.example.demo.repository.HotelRepository;
import com.example.demo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {
    private final HotelRepository hotelRepository;
    private final MongoTemplate mongoTemplate;
    public List<Hotel> findAll(){
        return hotelRepository.findAll();
    }
    public List<Hotel> findAllPages(Integer pageNumber, Integer pageSize) {
        return hotelRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
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
    public Hotel rate(String hotelId, Integer newMark) {
        Optional<Hotel> hotel = findById(hotelId);
        if (hotel.isPresent()) {
            Double rating = hotel.get().getRating();
            Integer numberOfRating = hotel.get().getNumberOfRating();
            double totalRating = rating * numberOfRating;
            totalRating = totalRating - rating + newMark;
            double notRoundedRating = numberOfRating == 0 ? totalRating : totalRating / numberOfRating;
            hotel.get().setRating(Math.ceil(notRoundedRating * 10) / 10);
            hotel.get().setNumberOfRating(++numberOfRating);
            return hotelRepository.save(hotel.get());
        }
        return null;
    }
    public List<Hotel> filterBy(HotelFilter filter){
        Query query = HotelSpecification.withFilter(filter);
        return mongoTemplate.find(query, Hotel.class);
    }
}
