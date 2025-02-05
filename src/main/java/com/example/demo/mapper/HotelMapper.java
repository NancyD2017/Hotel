package com.example.demo.mapper;

import com.example.demo.entity.Hotel;
import com.example.demo.web.model.request.UpsertHotelRequest;
import com.example.demo.web.model.response.HotelListResponse;
import com.example.demo.web.model.response.HotelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    HotelResponse hotelToResponse(Hotel hotel);

    default HotelListResponse hotelsToResponse(List<Hotel> hotels){
        HotelListResponse hotelListResponse = new HotelListResponse();
        hotelListResponse.setHotels(hotelListToResponseList(hotels));
        return hotelListResponse;
    }
    List<HotelResponse> hotelListToResponseList(List<Hotel> hotelList);

    Hotel requestToHotel(UpsertHotelRequest request);

}
