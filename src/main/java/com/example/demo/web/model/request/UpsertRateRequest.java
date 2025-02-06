package com.example.demo.web.model.request;

import lombok.Data;

@Data
public class UpsertRateRequest {
    private String hotelId;
    private Integer newMark;
}
