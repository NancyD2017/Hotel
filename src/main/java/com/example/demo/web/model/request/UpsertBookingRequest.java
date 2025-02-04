package com.example.demo.web.model.request;

import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UpsertBookingRequest {
    private String id;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private Room room;
    private User user;
}
