package com.niam.kardan.controller;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.Plan;
import com.niam.kardan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/plans")
public class PlanController {
    private final PlanService planService;
    private final ResponseEntityUtil responseEntityUtil;

    @PostMapping
    public ResponseEntity<ServiceResponse> savePlan(@RequestBody Plan plan) {
        return responseEntityUtil.ok(planService.savePlan(plan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updatePlan(@PathVariable Long id, @RequestBody Plan plan) {
        return responseEntityUtil.ok(planService.updatePlan(id, plan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return responseEntityUtil.ok("Plan deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> findPlan(@PathVariable Long id) {
        return responseEntityUtil.ok(planService.getPlan(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResponse> findAllPlans() {
        return responseEntityUtil.ok(planService.getAllPlans());
    }
}