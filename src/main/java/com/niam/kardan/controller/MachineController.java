package com.niam.kardan.controller;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.Machine;
import com.niam.kardan.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/machines")
public class MachineController {
    private final MachineService machineService;
    private final ResponseEntityUtil responseEntityUtil;

    @PostMapping
    public ResponseEntity<ServiceResponse> saveMachine(@RequestBody Machine machine) {
        return responseEntityUtil.ok(machineService.saveMachine(machine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateMachine(@PathVariable Long id, @RequestBody Machine machine) {
        return responseEntityUtil.ok(machineService.updateMachine(id, machine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return responseEntityUtil.ok("Machine deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> findMachine(@PathVariable Long id) {
        return responseEntityUtil.ok(machineService.getMachine(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResponse> findAllMachines() {
        return responseEntityUtil.ok(machineService.getAllMachines());
    }
}