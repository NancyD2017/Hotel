package com.example.demo.web.controller;

import com.example.demo.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.PrintWriter;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
@Slf4j
public class KafkaController {

    private final KafkaService kafkaService;

    @GetMapping("/downloadCSV")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> downloadCSV(){
        File file = new File("Statistics.csv");
        try {
            PrintWriter pw = new PrintWriter(file);
            kafkaService.getAll().forEach(pw::println);
            pw.flush();
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Impossible to write a file");
        }
        return ResponseEntity.status(HttpStatus.OK).body(file);
    }
}
