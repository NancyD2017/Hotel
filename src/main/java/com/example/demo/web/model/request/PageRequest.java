package com.example.demo.web.model.request;

import lombok.Data;

@Data
public class PageRequest {
    private int pageSize = 0;
    private int pageNumber = 0;
}
