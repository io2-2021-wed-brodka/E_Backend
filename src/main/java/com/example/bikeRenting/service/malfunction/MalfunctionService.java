package com.example.bikeRenting.service.malfunction;

import com.example.bikeRenting.dto.request.malfunction.AddMalfunctionRequestDTO;
import com.example.bikeRenting.dto.response.MalfunctionDTO;
import com.example.bikeRenting.dto.response.MalfunctionListDTO;

public interface MalfunctionService {

    MalfunctionListDTO getAllMalfunctions();

    MalfunctionDTO addNewMalfunction(AddMalfunctionRequestDTO addMalfunctionRequestDTO, String reportingUserName);

    void deleteMalfunction(Long malfunctionId);

}
