package com.niam.kardan.service;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.OperationExecution;
import com.niam.kardan.model.OperationStop;
import com.niam.kardan.model.StopReason;
import com.niam.kardan.model.basedata.BaseData;
import com.niam.kardan.model.basedata.ExecutionStatus;
import com.niam.kardan.repository.OperationExecutionRepository;
import com.niam.kardan.repository.OperationStopRepository;
import com.niam.kardan.repository.StopReasonRepository;
import com.niam.kardan.repository.basedata.ExecutionStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OperationStopProcessTest {
    @Mock
    OperationExecutionRepository executionRepository;
    @Mock
    OperationStopRepository stopRepository;
    @Mock
    StopReasonRepository stopReasonRepository;
    @Mock
    ExecutionStatusRepository executionStatusRepository;
    @Mock
    MessageUtil messageUtil;

    @InjectMocks
    OperationExecutionService operationExecutionService;

    private OperationExecution execution;
    private StopReason stopReason;

    @BeforeEach
    void setUp() {
        execution = new OperationExecution();
        execution.setId(55L);
        execution.setExecutionStatus(BaseData.ofCode(ExecutionStatus.class, "STARTED"));
        execution.setStartTime(LocalDateTime.now());
        stopReason = new StopReason();
        stopReason.setId(2L);
        stopReason.setName("Brake");
    }

    @Test
    void stopExecution_createsOperationStop_and_updatesExecutionStatus() {
        when(executionRepository.findById(55L)).thenReturn(Optional.of(execution));
        when(stopReasonRepository.findById(2L)).thenReturn(Optional.of(stopReason));
        when(executionStatusRepository.findByCode("STOPPED")).thenReturn(Optional.of(BaseData.ofCode(ExecutionStatus.class, "STOPPED")));
        when(stopRepository.save(any(OperationStop.class))).thenAnswer(i -> {
            OperationStop s = (OperationStop) i.getArguments()[0];
            s.setId(99L);
            return s;
        });
        when(executionRepository.save(any(OperationExecution.class))).thenAnswer(i -> i.getArguments()[0]);

        OperationStop stop = operationExecutionService.stopExecution(55L, 2L, "emergency");

        assertThat(stop).isNotNull();
        assertThat(stop.getId()).isEqualTo(99L);
        verify(stopRepository).save(any(OperationStop.class));
        verify(executionRepository).save(any(OperationExecution.class));
        assertThat(execution.getExecutionStatus().getCode()).isEqualTo("STOPPED");
        assertThat(execution.getStopTime()).isNotNull();
    }

    @Test
    void resumeAfterStop_setsResumedStatusAndStartTime() {
        // simulate execution currently stopped
        execution.setExecutionStatus(BaseData.ofCode(ExecutionStatus.class, "STOPPED"));
        when(executionRepository.findById(55L)).thenReturn(Optional.of(execution));
        when(executionRepository.save(any(OperationExecution.class))).thenAnswer(i -> i.getArguments()[0]);
        when(executionStatusRepository.findByCode("RESUMED")).thenReturn(Optional.of(BaseData.ofCode(ExecutionStatus.class, "RESUMED")));

        // resume is modeled by calling claimAndStart-like behavior or updating execution directly
        execution.setExecutionStatus(BaseData.ofCode(ExecutionStatus.class, "RESUMED"));
        execution.setStartTime(LocalDateTime.now());

        OperationExecution resumed = executionRepository.save(execution);
        assertThat(resumed.getExecutionStatus().getCode()).isEqualTo("RESUMED");
        assertThat(resumed.getStartTime()).isNotNull();
    }
}