package com.example.demo.service;

import com.example.demo.entity.Hotel;
import com.example.demo.filter.HotelFilter;
import com.example.demo.filter.HotelSpecification;
import com.example.demo.repository.HotelRepository;
import com.example.demo.utils.BeanUtils;
import com.example.demo.web.model.request.HotelFilterRequest;
import com.example.demo.web.model.request.UpsertRateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
    public List<Hotel> findAllPages(com.example.demo.web.model.request.PageRequest pageRequest) {
        if (pageRequest.getPageSize() > 0 && pageRequest.getPageNumber() >= 0)
            return hotelRepository.findAll(PageRequest.of(
                    pageRequest.getPageNumber(), pageRequest.getPageSize())
            ).getContent();
         else throw new IllegalArgumentException("Specify pageNumber and pageSize");
    }

    public Optional<Hotel> findById(String id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) return hotel;
        else throw new NoSuchElementException("Hotel with id " + id + " not found");
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
        throw new NoSuchElementException("Hotel with id " + id + " not found");
    }

    public void deleteById(String id){
        hotelRepository.deleteById(id);
    }
    public Hotel rate(UpsertRateRequest request) {
        if (request.getNewMark() > 5 || request.getNewMark() < 1)
            throw new IllegalArgumentException("Rate the hotel with mark 1, 2, 3, 4 or 5");

        Optional<Hotel> hotel = findById(request.getHotelId());
        if (hotel.isPresent()) {
            Double rating = hotel.get().getRating();
            Integer numberOfRating = hotel.get().getNumberOfRating();
            double totalRating = rating * numberOfRating;
            totalRating = totalRating - rating + request.getNewMark();
            double notRoundedRating = numberOfRating == 0 ? totalRating : totalRating / numberOfRating;
            hotel.get().setRating(Math.ceil(notRoundedRating * 10) / 10);
            hotel.get().setNumberOfRating(++numberOfRating);
            return hotelRepository.save(hotel.get());
        }
        else throw new NoSuchElementException("Hotel with id " + request.getHotelId() + " not found");
    }
    public List<Hotel> filterBy(HotelFilterRequest filter){
        HotelFilter hotelFilter = new HotelFilter();

        if (filter.getHotelId() != null) hotelFilter.setHotelId(filter.getHotelId());
        if (filter.getName() != null) hotelFilter.setName(filter.getName());
        if (filter.getHeader() != null) hotelFilter.setHeader(filter.getHeader());
        if (filter.getCity() != null) hotelFilter.setCity(filter.getCity());
        if (filter.getAddress() != null) hotelFilter.setAddress(filter.getAddress());
        if (filter.getCityCenterDistance() != null) hotelFilter.setCityCenterDistance(filter.getCityCenterDistance());
        if (filter.getRating() != null) hotelFilter.setRating(filter.getRating());
        if (filter.getNumberOfRating() != null) hotelFilter.setNumberOfRating(filter.getNumberOfRating());

        Query query = HotelSpecification.withFilter(hotelFilter);
        return mongoTemplate.find(query, Hotel.class);
    }
}
