package com.example.demo.filter;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class HotelSpecification {
    public static Query withFilter(HotelFilter filter) {
        Query query = new Query();

        if (filter.getName() != null && !filter.getName().isEmpty()) {
            query.addCriteria(Criteria.where("name").is(filter.getName()));
        }
        if (filter.getHeader() != null && !filter.getHeader().isEmpty()) {
            query.addCriteria(Criteria.where("header").is(filter.getHeader()));
        }
        if (filter.getCity() != null && !filter.getCity().isEmpty()) {
            query.addCriteria(Criteria.where("city").is(filter.getCity()));
        }
        if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
            query.addCriteria(Criteria.where("address").is(filter.getAddress()));
        }
        if (filter.getCityCenterDistance() != null) {
            query.addCriteria(Criteria.where("CityCenterDistance").lte(filter.getCityCenterDistance()));
        }
        if (filter.getNumberOfRating() != null) {
            query.addCriteria(Criteria.where("numberOfRating").gte(filter.getNumberOfRating()));
        }
        if (filter.getRating() != null) {
            query.addCriteria(Criteria.where("Rating").gte(filter.getRating()));
        }
        if (filter.getHotelId() != null) {
            query.addCriteria(Criteria.where("id").is(filter.getHotelId()));
        }

        return query;
    }
}
