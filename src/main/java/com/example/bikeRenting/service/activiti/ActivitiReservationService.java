package com.example.bikeRenting.service.activiti;

import com.example.bikeRenting.model.entity.enums.BikeStatus;
import com.example.bikeRenting.repository.BikeRepository;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivitiReservationService {

    private final BikeRepository bikeRepository;
    private final RuntimeService runtimeService;

    public ActivitiReservationService(BikeRepository bikeRepository, RuntimeService runtimeService) {
        this.bikeRepository = bikeRepository;
        this.runtimeService = runtimeService;
    }

    public void startReservationExpiration(long bikeId) {
        runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey("startReservationExpiration")
                .businessKey("" + bikeId)
                .variable("bikeId", bikeId)
                .start();
    }

    @Transactional
    public void cancelReservation(DelegateExecution execution) {
        var bikeId = (long)execution.getVariable("bikeId");

        var bike = bikeRepository.findById(bikeId).orElseThrow();
        bike.setStatus(BikeStatus.ACTIVE);
        bikeRepository.save(bike);
    }
}
