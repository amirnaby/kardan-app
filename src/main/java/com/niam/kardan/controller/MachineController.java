package com.niam.kardan.controller;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.Machine;
import com.niam.kardan.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/machines")
public class MachineController {
    private final MachineService machineService;
    private final ResponseEntityUtil responseEntityUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceResponse> saveMachine(@RequestBody Machine machine) {
        return responseEntityUtil.ok(machineService.create(machine));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateMachine(@PathVariable Long id, @RequestBody Machine machine) {
        return responseEntityUtil.ok(machineService.update(id, machine));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> deleteMachine(@PathVariable Long id) {
        machineService.delete(id);
        return responseEntityUtil.ok("Machine deleted successfully");
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> findMachine(@PathVariable Long id) {
        return responseEntityUtil.ok(machineService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping
    public ResponseEntity<ServiceResponse> findAllMachines() {
        return responseEntityUtil.ok(machineService.getAll());
    }
}