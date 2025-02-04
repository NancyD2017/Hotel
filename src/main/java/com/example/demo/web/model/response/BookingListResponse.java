package com.example.demo.web.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingListResponse {
    private List<BookingResponse> bookings = new ArrayList<>();
}
