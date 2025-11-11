package com.niam.kardan.service;

import com.niam.common.exception.EntityNotFoundException;
import com.niam.common.exception.IllegalStateException;
import com.niam.common.exception.ResultResponseStatus;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.*;
import com.niam.kardan.repository.*;
import com.niam.kardan.repository.basedata.ExecutionStatusRepository;
import com.niam.kardan.repository.basedata.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OperationExecutionService {

    private final PartOperationTaskRepository partOperationTaskRepository;
    private final OperationExecutionRepository operationExecutionRepository;
    private final OperationStopRepository operationStopRepository;
    private final MachineRepository machineRepository;
    private final OperatorRepository operatorRepository;
    private final OperatorMachineRepository operatorMachineRepository;
    private final StopReasonRepository stopReasonRepository;
    private final ExecutionStatusRepository executionStatusRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final MessageUtil messageUtil;

    /**
     * Claim and start a task.
     * Concurrency safe: uses PESSIMISTIC_WRITE on task row.
     */
    @Transactional
    public OperationExecution claimAndStartTask(Long taskId, Long operatorId, Long machineId) {
        PartOperationTask task = partOperationTaskRepository.findByIdForUpdate(taskId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                        ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                        messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(), "PartOperationTask")));

        // validate task status = PENDING
        if (!"PENDING".equalsIgnoreCase(task.getTaskStatus().getCode())) {
            throw new IllegalStateException(messageUtil.getMessage("task.not.pending", String.valueOf(taskId)));
        }

        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                        ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                        messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(), "Operator")));

        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                        ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                        messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(), "Machine")));

        // check that the task targetMachine equals the provided machine
        if (!task.getTargetMachine().getId().equals(machine.getId())) {
            throw new IllegalStateException(messageUtil.getMessage("task.machine.mismatch", String.valueOf(taskId)));
        }

        // check operator authorized for machine (must have active OperatorMachine)
        boolean operatorCanUse = operatorMachineRepository.existsByOperatorIdAndMachineIdAndUnassignedAtIsNull(operator.getId(), machine.getId());
        if (!operatorCanUse) {
            throw new IllegalStateException(messageUtil.getMessage("operator.notAssignedToMachine", operator.getUser().getUsername()));
        }

        // mark task as claimed and started
        task.setClaimedBy(operator);
        task.setClaimedAt(LocalDateTime.now());
        task.setStartedAt(LocalDateTime.now());
        // change task status to IN_PROGRESS
        task.setTaskStatus(taskStatusRepository.findByCode("IN_PROGRESS")
                .orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("taskStatus.not.found", "IN_PROGRESS"))));

        partOperationTaskRepository.save(task);

        // create OperationExecution
        OperationExecution exec = new OperationExecution();
        exec.setTask(task);
        exec.setPartOperation(task.getPartOperation());
        exec.setMachine(machine);
        exec.setOperator(operator);
        exec.setStartTime(LocalDateTime.now());
        exec.setExecutionStatus(executionStatusRepository.findByCode("STARTED")
                .orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("executionStatus.not.found", "STARTED"))));
        return operationExecutionRepository.save(exec);
    }

    /**
     * Stop execution with a reason (creates OperationStop and marks execution stopped).
     */
    @Transactional
    public OperationStop stopExecution(Long executionId, Long stopReasonId, String comment) {
        OperationExecution exec = operationExecutionRepository.findById(executionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                        ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                        messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(), "OperationExecution")));

        StopReason stopReason = stopReasonRepository.findById(stopReasonId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                        ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                        messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(), "StopReason")));

        // تغییر وضعیت اجرا
        exec.setStopTime(LocalDateTime.now());
        exec.setExecutionStatus(executionStatusRepository.findByCode("STOPPED")
                .orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("executionStatus.not.found", "STOPPED"))));
        operationExecutionRepository.save(exec);

        // ثبت توقف
        OperationStop stop = new OperationStop();
        stop.setOperationExecution(exec);
        stop.setStopReason(stopReason);
        stop.setComment(comment);
        stop.setStartedAt(LocalDateTime.now());
        return operationStopRepository.save(stop);
    }

    /**
     * Finish execution: mark execution completed and update task status to COMPLETED.
     */
    @Transactional
    public OperationExecution finishExecution(Long executionId) {
        OperationExecution exec = operationExecutionRepository.findById(executionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                        ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                        messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(), "OperationExecution")));

        if (!"STARTED".equalsIgnoreCase(exec.getExecutionStatus().getCode())
                && !"STOPPED".equalsIgnoreCase(exec.getExecutionStatus().getCode())) {
            throw new IllegalStateException(messageUtil.getMessage("execution.notStartOrStopped", String.valueOf(executionId)));
        }

        exec.setEndTime(LocalDateTime.now());
        exec.setExecutionStatus(executionStatusRepository.findByCode("COMPLETED")
                .orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("executionStatus.not.found", "COMPLETED"))));

        // update task finishedAt and status
        PartOperationTask task = exec.getTask();
        task.setFinishedAt(LocalDateTime.now());
        task.setTaskStatus(taskStatusRepository.findByCode("COMPLETED")
                .orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("taskStatus.not.found", "COMPLETED"))));

        partOperationTaskRepository.save(task);
        return operationExecutionRepository.save(exec);
    }

    @Transactional(readOnly = true)
    public OperationExecution getById(Long id) {
        return operationExecutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                        ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                        messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(), "OperationExecution")));
    }
}