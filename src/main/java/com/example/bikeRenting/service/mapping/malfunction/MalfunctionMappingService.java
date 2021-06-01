package com.example.bikeRenting.service.mapping.malfunction;

import com.example.bikeRenting.dto.response.MalfunctionDTO;
import com.example.bikeRenting.model.entity.Malfunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MalfunctionMappingService {

    public MalfunctionDTO mapToMalfunctionDTO(Malfunction malfunction) {
        return MalfunctionDTO.builder()
                .id(malfunction.getId())
                .bikeId(malfunction.getBike().getId())
                .description(malfunction.getDescription())
                .reportingUserId(malfunction.getReportingUser().getId())
                .build();
    }

}
