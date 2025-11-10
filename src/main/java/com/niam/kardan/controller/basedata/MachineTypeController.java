package com.niam.kardan.controller.basedata;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.basedata.MachineType;
import com.niam.kardan.service.basedata.MachineTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/basedata/machine-types")
public class MachineTypeController {
    private final MachineTypeService machineTypeService;
    private final ResponseEntityUtil responseEntityUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceResponse> create(@RequestBody MachineType entity) {
        return responseEntityUtil.ok(machineTypeService.create(entity));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @RequestBody MachineType entity) {
        return responseEntityUtil.ok(machineTypeService.update(id, entity));
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable Long id) {
        return responseEntityUtil.ok(machineTypeService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping
    public ResponseEntity<ServiceResponse> getAll() {
        return responseEntityUtil.ok(machineTypeService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> delete(@PathVariable Long id) {
        machineTypeService.delete(id);
        return responseEntityUtil.ok("Deleted successfully");
    }
}