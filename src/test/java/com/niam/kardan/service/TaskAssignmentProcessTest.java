package com.niam.kardan.service;

import com.niam.common.exception.IllegalStateException;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.Machine;
import com.niam.kardan.model.OperationExecution;
import com.niam.kardan.model.Operator;
import com.niam.kardan.model.PartOperationTask;
import com.niam.kardan.model.basedata.BaseData;
import com.niam.kardan.model.basedata.ExecutionStatus;
import com.niam.kardan.model.basedata.TaskStatus;
import com.niam.kardan.repository.*;
import com.niam.kardan.repository.basedata.ExecutionStatusRepository;
import com.niam.kardan.repository.basedata.TaskStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskAssignmentProcessTest {
    @Mock
    PartOperationTaskRepository partOperationTaskRepository;
    @Mock
    OperationExecutionRepository operationExecutionRepository;
    @Mock
    MachineRepository machineRepository;
    @Mock
    OperatorRepository operatorRepository;
    @Mock
    OperatorMachineRepository operatorMachineRepository;
    @Mock
    TaskStatusRepository taskStatusRepository;
    @Mock
    ExecutionStatusRepository executionStatusRepository;
    @Mock
    MessageUtil messageUtil;

    @InjectMocks
    OperationExecutionService operationExecutionService;

    private PartOperationTask task;
    private Operator op1;
    private Operator op2;
    private Machine machine;

    @BeforeEach
    void setup() {
        task = new PartOperationTask();
        task.setId(500L);
        task.setTaskStatus(BaseData.ofCode(TaskStatus.class, "PENDING"));
        machine = new Machine();
        machine.setId(700L);
        task.setTargetMachine(machine);
        op1 = new Operator();
        op1.setId(1L);
        op2 = new Operator();
        op2.setId(2L);
    }

    @Test
    void firstOperatorWinsClaimSecondFails() {
        // first call: repository returns PENDING, operator assigned -> success
        when(partOperationTaskRepository.findByIdForUpdate(500L)).thenReturn(Optional.of(task));
        when(operatorRepository.findById(1L)).thenReturn(Optional.of(op1));
        when(machineRepository.findById(700L)).thenReturn(Optional.of(machine));
        when(operatorMachineRepository.existsByOperatorIdAndMachineIdAndUnassignedAtIsNull(1L, 700L)).thenReturn(true);
        when(taskStatusRepository.findByCode("IN_PROGRESS")).thenReturn(Optional.of(BaseData.ofCode(TaskStatus.class, "IN_PROGRESS")));
        when(executionStatusRepository.findByCode("STARTED")).thenReturn(Optional.of(BaseData.ofCode(ExecutionStatus.class, "STARTED")));
        when(operationExecutionRepository.save(any(OperationExecution.class))).thenAnswer(i -> i.getArguments()[0]);
        when(partOperationTaskRepository.save(any(PartOperationTask.class))).thenAnswer(i -> i.getArguments()[0]);

        OperationExecution exec1 = operationExecutionService.claimAndStartTask(500L, 1L, 700L);
        assertThat(exec1).isNotNull();

        // second operator tries: simulate that task now not PENDING
        task.setTaskStatus(BaseData.ofCode(TaskStatus.class, "IN_PROGRESS"));
        when(partOperationTaskRepository.findByIdForUpdate(500L)).thenReturn(Optional.of(task));
        assertThatThrownBy(() -> operationExecutionService.claimAndStartTask(500L, 2L, 700L))
                .isInstanceOf(IllegalStateException.class);
    }
}