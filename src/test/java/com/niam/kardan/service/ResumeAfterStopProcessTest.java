package com.niam.kardan.service;

import com.niam.kardan.model.OperationExecution;
import com.niam.kardan.model.OperationStop;
import com.niam.kardan.model.basedata.BaseData;
import com.niam.kardan.model.basedata.ExecutionStatus;
import com.niam.kardan.model.basedata.enums.EXECUTION_STATUS;
import com.niam.kardan.repository.OperationExecutionRepository;
import com.niam.kardan.repository.OperationStopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResumeAfterStopProcessTest {
    @Mock
    private OperationExecutionRepository executionRepository;
    @Mock
    private OperationStopRepository stopRepository;
    @InjectMocks
    private OperationExecutionService service;

    private OperationExecution exec;
    private OperationStop stop;

    @BeforeEach
    void setup() {
        exec = new OperationExecution();
        exec.setId(1L);
        exec.setExecutionStatus(BaseData.ofCode(ExecutionStatus.class, EXECUTION_STATUS.STOPPED.name()));
        stop = new OperationStop();
        stop.setId(5L);
        stop.setOperationExecution(exec);
        stop.setEndedAt(LocalDateTime.now());
    }

    @Test
    void resumeAfterStop_setsResumedStatusAndStartTime() {
        when(executionRepository.findById(1L)).thenReturn(Optional.of(exec));
        when(executionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        OperationExecution resumed = service.resumeAfterStop(1L);

        assertThat(resumed.getExecutionStatus().getCode()).isEqualTo(EXECUTION_STATUS.RUNNING.name());
        assertThat(resumed.getStartTime()).isNotNull();
        verify(executionRepository, times(1)).save(resumed);
    }
}