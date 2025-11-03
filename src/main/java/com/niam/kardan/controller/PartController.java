package com.niam.kardan.controller;

import com.niam.common.model.response.ServiceResponse;
import com.niam.common.utils.ResponseEntityUtil;
import com.niam.kardan.model.Part;
import com.niam.kardan.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parts")
public class PartController {
    private final PartService partService;
    private final ResponseEntityUtil responseEntityUtil;

    @PostMapping
    public ResponseEntity<ServiceResponse> savePart(@RequestBody Part part) {
        return responseEntityUtil.ok(partService.savePart(part));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updatePart(@PathVariable Long id, @RequestBody Part part) {
        return responseEntityUtil.ok(partService.updatePart(id, part));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResponse> deletePart(@PathVariable Long id) {
        partService.deletePart(id);
        return responseEntityUtil.ok("Part deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> findPart(@PathVariable Long id) {
        return responseEntityUtil.ok(partService.getPart(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResponse> findAllParts() {
        return responseEntityUtil.ok(partService.getAllParts());
    }
}