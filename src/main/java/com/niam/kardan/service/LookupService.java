package com.niam.kardan.service;

import com.niam.kardan.repository.basedata.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LookupService {
    private final ExecutionStatusRepository executionStatusRepository;
    private final MachineStatusRepository machineStatusRepository;
    private final MachineTypeRepository machineTypeRepository;
    private final PartOperationStatusRepository partOperationStatusRepository;
    private final PartStatusRepository partStatusRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final ShiftStatusRepository shiftStatusRepository;
    private final StopReasonCategoryRepository stopReasonCategoryRepository;
    private final TaskStatusRepository taskStatusRepository;

    public Map<String, Object> getAllLookups() {
        Map<String, Object> map = new HashMap<>();
        map.put("executionStatuses", executionStatusRepository.findAll());
        map.put("machineStatuses", machineStatusRepository.findAll());
        map.put("machineTypes", machineTypeRepository.findAll());
        map.put("partOperationStatuses", partOperationStatusRepository.findAll());
        map.put("partStatuses", partStatusRepository.findAll());
        map.put("projectStatuses", projectStatusRepository.findAll());
        map.put("shiftStatuses", shiftStatusRepository.findAll());
        map.put("stopReasonCategories", stopReasonCategoryRepository.findAll());
        map.put("taskStatuses", taskStatusRepository.findAll());
        return map;
    }
}