package com.niam.kardan.service;

import com.niam.common.exception.IllegalStateException;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.*;
import com.niam.kardan.model.basedata.BaseData;
import com.niam.kardan.model.basedata.ExecutionStatus;
import com.niam.kardan.model.basedata.TaskStatus;
import com.niam.kardan.repository.*;
import com.niam.kardan.repository.basedata.ExecutionStatusRepository;
import com.niam.kardan.repository.basedata.TaskStatusRepository;
import com.niam.usermanagement.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationExecutionProcessTest {
    @Mock
    PartOperationTaskRepository partOperationTaskRepository;
    @Mock
    OperationExecutionRepository operationExecutionRepository;
    @Mock
    OperationStopRepository operationStopRepository;
    @Mock
    MachineRepository machineRepository;
    @Mock
    OperatorRepository operatorRepository;
    @Mock
    OperatorMachineRepository operatorMachineRepository;
    @Mock
    ExecutionStatusRepository executionStatusRepository;
    @Mock
    TaskStatusRepository taskStatusRepository;
    @Mock
    MessageUtil messageUtil;

    @InjectMocks
    OperationExecutionService operationExecutionService;

    private PartOperationTask task;
    private Operator operator;
    private Machine machine;

    @BeforeEach
    void setUp() {
        task = new PartOperationTask();
        task.setId(100L);
        task.setTaskStatus(BaseData.ofCode(TaskStatus.class, "PENDING"));
        Machine target = new Machine();
        target.setId(200L);
        target.setCode("M-1");
        task.setTargetMachine(target);
        task.setPartOperation(new PartOperation());

        operator = new Operator();
        operator.setId(10L);
        machine = target;

        User user = new User();
        user.setUsername("testuser");
        operator.setUser(user);

    }

    @Test
    void claimAndStartTask_success_then_finish() {
        when(partOperationTaskRepository.findByIdForUpdate(100L)).thenReturn(Optional.of(task));
        when(operatorRepository.findById(10L)).thenReturn(Optional.of(operator));
        when(machineRepository.findById(200L)).thenReturn(Optional.of(machine));
        when(operatorMachineRepository.existsByOperatorIdAndMachineIdAndUnassignedAtIsNull(10L, 200L)).thenReturn(true);
        when(taskStatusRepository.findByCode("IN_PROGRESS")).thenReturn(Optional.of(BaseData.ofCode(TaskStatus.class, "IN_PROGRESS")));
        when(executionStatusRepository.findByCode("STARTED")).thenReturn(Optional.of(BaseData.ofCode(ExecutionStatus.class, "STARTED")));
        when(operationExecutionRepository.save(any(OperationExecution.class))).thenAnswer(i -> i.getArguments()[0]);
        when(partOperationTaskRepository.save(any(PartOperationTask.class))).thenAnswer(i -> i.getArguments()[0]);

        OperationExecution exec = operationExecutionService.claimAndStartTask(100L, 10L, 200L);

        assertThat(exec).isNotNull();
        assertThat(exec.getOperator().getId()).isEqualTo(10L);
        assertThat(task.getTaskStatus().getCode()).isEqualTo("IN_PROGRESS");
        verify(partOperationTaskRepository).save(task);
        verify(operationExecutionRepository).save(any(OperationExecution.class));

        // mocks for finish
        when(executionStatusRepository.findByCode("COMPLETED")).thenReturn(Optional.of(BaseData.ofCode(ExecutionStatus.class, "COMPLETED")));
        when(taskStatusRepository.findByCode("COMPLETED")).thenReturn(Optional.of(BaseData.ofCode(TaskStatus.class, "COMPLETED")));
        when(operationExecutionRepository.findById(anyLong())).thenReturn(Optional.of(exec));
        when(partOperationTaskRepository.save(any(PartOperationTask.class))).thenAnswer(i -> i.getArguments()[0]);
        when(operationExecutionRepository.save(any(OperationExecution.class))).thenAnswer(i -> i.getArguments()[0]);

        OperationExecution finished = operationExecutionService.finishExecution(exec.getId() == null ? 0L : exec.getId());

        assertThat(finished.getExecutionStatus().getCode()).isEqualTo("COMPLETED");
        assertThat(finished.getEndTime()).isNotNull();
        verify(partOperationTaskRepository, atLeastOnce()).save(any(PartOperationTask.class));
    }

    @Test
    void claimAndStartTask_whenNotPending_throws() {
        task.setTaskStatus(BaseData.ofCode(TaskStatus.class, "CLAIMED"));
        when(partOperationTaskRepository.findByIdForUpdate(100L)).thenReturn(Optional.of(task));

        assertThatThrownBy(() -> operationExecutionService.claimAndStartTask(100L, 10L, 200L))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void claimAndStartTask_whenOperatorNotAssigned_throws() {
        when(partOperationTaskRepository.findByIdForUpdate(100L)).thenReturn(Optional.of(task));
        when(operatorRepository.findById(10L)).thenReturn(Optional.of(operator));
        when(machineRepository.findById(200L)).thenReturn(Optional.of(machine));
        when(operatorMachineRepository.existsByOperatorIdAndMachineIdAndUnassignedAtIsNull(10L, 200L)).thenReturn(false);

        assertThatThrownBy(() -> operationExecutionService.claimAndStartTask(100L, 10L, 200L))
                .isInstanceOf(IllegalStateException.class);
    }
}