package com.example.bikeRenting.service.malfunction;


import com.example.bikeRenting.dto.request.malfunction.AddMalfunctionRequestDTO;
import com.example.bikeRenting.dto.response.MalfunctionDTO;
import com.example.bikeRenting.dto.response.MalfunctionListDTO;
import com.example.bikeRenting.model.entity.Bike;
import com.example.bikeRenting.model.entity.Malfunction;
import com.example.bikeRenting.repository.MalfunctionRepository;
import com.example.bikeRenting.repository.UserRepository;
import com.example.bikeRenting.service.mapping.malfunction.MalfunctionMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class MalfunctionMainService implements MalfunctionService {

    private final MalfunctionRepository malfunctionRepository;
    private final MalfunctionMappingService malfunctionMappingService;
    private final UserRepository userRepository;

    public MalfunctionMainService(MalfunctionRepository malfunctionRepository, MalfunctionMappingService malfunctionMappingService, UserRepository userRepository) {
        this.malfunctionRepository = malfunctionRepository;
        this.malfunctionMappingService = malfunctionMappingService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public MalfunctionListDTO getAllMalfunctions() {
        return MalfunctionListDTO
                .builder()
                .malfunctions(malfunctionRepository.findAll()
                        .stream()
                        .map(malfunctionMappingService::mapToMalfunctionDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public MalfunctionDTO addNewMalfunction(AddMalfunctionRequestDTO addMalfunctionRequestDTO, String reportingUserName) {
        Malfunction newMalfunction = Malfunction.builder()
                .bike(new Bike(addMalfunctionRequestDTO.getId()))
                .description(addMalfunctionRequestDTO.getDescription())
                .reportingUser(userRepository.findByUserName(reportingUserName).orElseThrow())
                .detectionDate(LocalDateTime.now())
                .isFixed(false)
                .build();

        Malfunction savedMalfunction = malfunctionRepository.save(newMalfunction);
        return malfunctionMappingService.mapToMalfunctionDTO(savedMalfunction);
    }

    @Transactional
    public void deleteMalfunction(Long malfunctionId) {
        malfunctionRepository.deleteById(malfunctionId);
    }

}
