package com.example.bikeRenting.service.time;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DateTimeService {

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }
}
