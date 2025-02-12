package com.example.demo.web.controller;

import com.example.demo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/downloadCSV")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> downloadCSV(){
        File file = statisticsService.downloadCSV();
        return file == null ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Impossible to write a file")
                : ResponseEntity.status(HttpStatus.OK).body(file);
    }
}
