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
    default BookingListResponse bookingsToResponse(List<Booking> bookings){
        BookingListResponse bookingListResponse = new BookingListResponse();
        bookingListResponse.setBookings(bookingListToResponseList(bookings));
        return bookingListResponse;
    }
    List<BookingResponse> bookingListToResponseList(List<Booking> bookingList);
    BookingResponse bookingToResponse(Booking booking);
}
