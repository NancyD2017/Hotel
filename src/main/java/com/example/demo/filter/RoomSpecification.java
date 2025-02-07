package com.example.demo.filter;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class RoomSpecification {
    public static Query withFilter(RoomFilter filter) {
        Query query = new Query();

        if (filter.getRoomId() != null && !filter.getRoomId().isEmpty()) {
            query.addCriteria(Criteria.where("roomId").is(filter.getRoomId()));
        }
        if (filter.getHeader() != null && !filter.getHeader().isEmpty()) {
            query.addCriteria(Criteria.where("hotel.header").is(filter.getHeader()));
        }
        if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
            query.addCriteria(Criteria.where("minPrice").gte(filter.getMinPrice()));
        }
        if (filter.getMaxPrice() != null && filter.getMinPrice() != null) {
            query.addCriteria(Criteria.where("maxPrice").lte(filter.getMaxPrice()));
        }
        if (filter.getMaximumGuestsCapacity() != null) {
            query.addCriteria(Criteria.where("maximumGuestsCapacity").gte(filter.getMaximumGuestsCapacity()));
        }
        if (filter.getMoveInDate() != null && filter.getMoveOutDate() != null) {
            Set<LocalDate> desiredDates = new HashSet<>();
            LocalDate firstDate = filter.getMoveInDate();
            while (!firstDate.isAfter(filter.getMoveOutDate())) {
                desiredDates.add(firstDate);
                firstDate = firstDate.plusDays(1);
            }
            query.addCriteria(Criteria.where("alreadyBookedDates").nin(desiredDates));
        }
        if (filter.getHotelId() != null) {
            query.addCriteria(Criteria.where("hotel.id").is(filter.getHotelId()));
        }

        return query;
    }
}
