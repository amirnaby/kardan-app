package com.niam.kardan.controller;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.PartOperationTask;
import com.niam.kardan.service.PartOperationTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/part-operation-tasks")
public class PartOperationTaskController {
    private final PartOperationTaskService partOperationTaskService;
    private final ResponseEntityUtil responseEntityUtil;

    @PostMapping
    public ResponseEntity<ServiceResponse> create(@RequestBody PartOperationTask task) {
        return responseEntityUtil.ok(partOperationTaskService.create(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @RequestBody PartOperationTask task) {
        return responseEntityUtil.ok(partOperationTaskService.update(id, task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable Long id) {
        return responseEntityUtil.ok(partOperationTaskService.getById(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResponse> getAll() {
        return responseEntityUtil.ok(partOperationTaskService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> delete(@PathVariable Long id) {
        partOperationTaskService.delete(id);
        return responseEntityUtil.ok("Task deleted successfully");
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<ServiceResponse> complete(@PathVariable Long id) {
        return responseEntityUtil.ok(partOperationTaskService.markAsCompleted(id));
    }
}