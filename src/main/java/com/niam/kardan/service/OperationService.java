package com.niam.kardan.service;

import com.niam.common.exception.EntityNotFoundException;
import com.niam.common.exception.ResultResponseStatus;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.Operation;
import com.niam.kardan.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final MessageUtil messageUtil;

    public Operation saveOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    public Operation updateOperation(Long id, Operation updatedOperation) {
        Operation existingOperation = getOperation(id);
        return operationRepository.save(existingOperation);
    }

    public void deleteOperation(Long id) {
        Operation operation = getOperation(id);
        operationRepository.delete(operation);
    }

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public Operation getOperation(Long id) {
        return operationRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                                ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                                messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(),
                                        "Operation")));
    }
}