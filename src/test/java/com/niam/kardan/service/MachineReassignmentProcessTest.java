package com.niam.kardan.service;

import com.niam.kardan.model.Machine;
import com.niam.kardan.model.OperationExecution;
import com.niam.kardan.model.basedata.BaseData;
import com.niam.kardan.model.basedata.ExecutionStatus;
import com.niam.kardan.model.basedata.enums.EXECUTION_STATUS;
import com.niam.kardan.repository.OperationExecutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MachineReassignmentProcessTest {
    @Mock
    private OperationExecutionRepository executionRepository;
    @Mock
    private MachineService machineService;
    @InjectMocks
    private OperationExecutionService service;

    private OperationExecution exec;
    private Machine newMachine;

    @BeforeEach
    void setup() {
        exec = new OperationExecution();
        exec.setId(1L);
        exec.setExecutionStatus(BaseData.ofCode(ExecutionStatus.class, EXECUTION_STATUS.RUNNING.name()));
        newMachine = new Machine();
        newMachine.setId(99L);
    }

    @Test
    void reassignMachine_success() {
        when(executionRepository.findById(1L)).thenReturn(Optional.of(exec));
        when(machineService.getById(99L)).thenReturn(newMachine);
        when(executionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        OperationExecution updated = service.reassignMachine(1L, 99L);

        assertThat(updated.getMachine()).isEqualTo(newMachine);
        verify(executionRepository, times(1)).save(updated);
    }
}