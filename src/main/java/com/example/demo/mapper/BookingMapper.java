package com.example.demo.mapper;

import com.example.demo.entity.Booking;
import com.example.demo.web.model.request.UpsertBookingRequest;
import com.example.demo.web.model.response.BookingListResponse;
import com.example.demo.web.model.response.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {
    Booking requestToBooking(UpsertBookingRequest request);
    BookingListResponse bookingsToResponse(List<Booking> booking);
    BookingResponse bookingToResponse(Booking booking);
}
