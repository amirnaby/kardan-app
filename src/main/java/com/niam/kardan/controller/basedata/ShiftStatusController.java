package com.niam.kardan.controller.basedata;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.basedata.ShiftStatus;
import com.niam.kardan.service.basedata.ShiftStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/basedata/shift-statuses")
public class ShiftStatusController {
    private final ShiftStatusService shiftStatusService;
    private final ResponseEntityUtil responseEntityUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceResponse> create(@RequestBody ShiftStatus entity) {
        return responseEntityUtil.ok(shiftStatusService.create(entity));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @RequestBody ShiftStatus entity) {
        return responseEntityUtil.ok(shiftStatusService.update(id, entity));
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable Long id) {
        return responseEntityUtil.ok(shiftStatusService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping
    public ResponseEntity<ServiceResponse> getAll() {
        return responseEntityUtil.ok(shiftStatusService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> delete(@PathVariable Long id) {
        shiftStatusService.delete(id);
        return responseEntityUtil.ok("Deleted successfully");
    }
}