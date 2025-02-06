package com.example.demo.mapper;

import com.example.demo.web.model.response.HotelListResponse;
import com.example.demo.web.model.response.PageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PageMapper {
    default PageResponse hotelListResponseToPageResponse(HotelListResponse hotelListResponse){
        PageResponse pr = new PageResponse();
        pr.setHotelListResponse(hotelListResponse);
        pr.setEntityNumber(hotelListResponse.getHotels().size());
        return pr;
    }
}
