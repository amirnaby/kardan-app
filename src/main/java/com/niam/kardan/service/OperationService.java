package com.niam.kardan.service;

import com.niam.common.exception.EntityNotFoundException;
import com.niam.common.exception.ResultResponseStatus;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.PartOperation;
import com.niam.kardan.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final MessageUtil messageUtil;

    public PartOperation saveOperation(PartOperation partOperation) {
        return operationRepository.save(partOperation);
    }

    public PartOperation updateOperation(Long id, PartOperation updatedPartOperation) {
        PartOperation existingPartOperation = getOperation(id);
        return operationRepository.save(existingPartOperation);
    }

    public void deleteOperation(Long id) {
        PartOperation partOperation = getOperation(id);
        operationRepository.delete(partOperation);
    }

    public List<PartOperation> getAllOperations() {
        return operationRepository.findAll();
    }

    public PartOperation getOperation(Long id) {
        return operationRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                                ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                                messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(),
                                        "Operation")));
    }
}