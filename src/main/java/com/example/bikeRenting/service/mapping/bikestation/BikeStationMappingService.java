package com.example.bikeRenting.service.mapping.bikestation;

import com.example.bikeRenting.dto.response.BikeDTO;
import com.example.bikeRenting.dto.response.BikeStationDTO;
import com.example.bikeRenting.model.entity.BikeStation;
import com.example.bikeRenting.model.entity.enums.BikeStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BikeStationMappingService {
    public BikeStationDTO mapToBikeStationDTO(BikeStation bikeStation)
    {
        var result = new BikeStationDTO();
        result.setId(bikeStation.getId());
        result.setBikesLimit(bikeStation.getMaxBikes());
        result.setActiveBikesCount((int)bikeStation.getBikes().stream().filter(x->x.getStatus()== BikeStatus.ACTIVE).count());
        result.setName(bikeStation.getLocationName());
        result.setStatus(Status2Status(bikeStation.getStatus()));
        return result;
    }

    private BikeStationDTO.StationState Status2Status(BikeStation.BikeStationState state)
    {
        switch (state)
        {
            case Blocked:
                return BikeStationDTO.StationState.blocked;
            case Working:
                return BikeStationDTO.StationState.active;
            case Deleted:
                return BikeStationDTO.StationState.deleted;
        }
        return BikeStationDTO.StationState.deleted;
    }
}
