package com.niam.kardan.controller.basedata;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.basedata.MachineStatus;
import com.niam.kardan.service.basedata.MachineStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/basedata/machine-statuses")
public class MachineStatusController {
    private final MachineStatusService machineStatusService;
    private final ResponseEntityUtil responseEntityUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceResponse> create(@RequestBody MachineStatus entity) {
        return responseEntityUtil.ok(machineStatusService.create(entity));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @RequestBody MachineStatus entity) {
        return responseEntityUtil.ok(machineStatusService.update(id, entity));
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable Long id) {
        return responseEntityUtil.ok(machineStatusService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping
    public ResponseEntity<ServiceResponse> getAll() {
        return responseEntityUtil.ok(machineStatusService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> delete(@PathVariable Long id) {
        machineStatusService.delete(id);
        return responseEntityUtil.ok("Deleted successfully");
    }
}