package com.niam.kardan.controller;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.PartOperation;
import com.niam.kardan.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/operations")
public class OperationController {
    private final OperationService operationService;
    private final ResponseEntityUtil responseEntityUtil;

    @PostMapping
    public ResponseEntity<ServiceResponse> saveOperation(@RequestBody PartOperation partOperation) {
        return responseEntityUtil.ok(operationService.saveOperation(partOperation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateOperation(@PathVariable Long id, @RequestBody PartOperation partOperation) {
        return responseEntityUtil.ok(operationService.updateOperation(id, partOperation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> deleteOperation(@PathVariable Long id) {
        operationService.deleteOperation(id);
        return responseEntityUtil.ok("Operation deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> findOperation(@PathVariable Long id) {
        return responseEntityUtil.ok(operationService.getOperation(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResponse> findAllOperations() {
        return responseEntityUtil.ok(operationService.getAllOperations());
    }
}