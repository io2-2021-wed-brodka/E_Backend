package com.example.bikeRenting.service.activiti;

import com.example.bikeRenting.model.entity.enums.BikeStatus;
import com.example.bikeRenting.repository.BikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
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

    public void cancelReservationExpiration(long bikeId) {

        cancelReservation(bikeId);

        var process = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("startReservationExpiration")
                .processInstanceBusinessKey("" + bikeId)
                .singleResult();
        if(process == null) {
            return;
        }

        log.info("canceling reservation expiration for bike {}", bikeId);

        runtimeService.deleteProcessInstance(process.getId(), null);
    }

    @Transactional
    public void cancelReservation(DelegateExecution execution) {
        var bikeId = (long) execution.getVariable("bikeId");

        cancelReservation(bikeId);
    }

    @Transactional
    public void cancelReservation(long bikeId) {
        var bike = bikeRepository.findById(bikeId).orElseThrow();
        bike.setStatus(BikeStatus.ACTIVE);
        bike.setReservation(null);
        bikeRepository.save(bike);

        log.info("reservation for bike {} has expired", bikeId);
    }
}
