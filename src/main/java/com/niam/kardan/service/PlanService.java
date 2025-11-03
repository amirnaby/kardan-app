package com.niam.kardan.service;

import com.niam.common.exception.EntityNotFoundException;
import com.niam.common.exception.ResultResponseStatus;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.Plan;
import com.niam.kardan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final MessageUtil messageUtil;

    public Plan savePlan(Plan plan) {
        return planRepository.save(plan);
    }

    public Plan updatePlan(Long id, Plan updatedPlan) {
        Plan existingPlan = getPlan(id);
        return planRepository.save(existingPlan);
    }

    public void deletePlan(Long id) {
        Plan plan = getPlan(id);
        planRepository.delete(plan);
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public Plan getPlan(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                                ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                                messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(),
                                        "Plan")));
    }
}