package com.niam.kardan.service;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.Operator;
import com.niam.kardan.model.OperatorShift;
import com.niam.kardan.model.Shift;
import com.niam.kardan.repository.OperatorRepository;
import com.niam.kardan.repository.OperatorShiftRepository;
import com.niam.kardan.repository.ShiftRepository;
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
class OperatorShiftProcessTest {
    @Mock
    OperatorShiftRepository operatorShiftRepository;
    @Mock
    OperatorRepository operatorRepository;
    @Mock
    ShiftRepository shiftRepository;
    @Mock
    MessageUtil messageUtil;

    @InjectMocks
    OperatorShiftService operatorShiftService;

    private Operator operator;
    private Shift shift;

    @BeforeEach
    void setUp() {
        operatorShiftService.self = operatorShiftService;
        operator = new Operator();
        operator.setId(11L);
        shift = new Shift();
        shift.setId(21L);
        shift.setName("S1");
    }

    @Test
    void assignAndUnassignShift_success() {
        OperatorShift os = new OperatorShift();
        os.setId(1000L);
        os.setOperator(operator);
        os.setShift(shift);

        when(operatorShiftRepository.existsByOperatorIdAndUnassignedAtIsNull(operator.getId())).thenReturn(false);
        when(operatorShiftRepository.save(any(OperatorShift.class))).thenAnswer(i -> {
            OperatorShift arg = i.getArgument(0);
            arg.setId(1000L);
            return arg;
        });

        OperatorShift saved = operatorShiftService.create(os);
        assertThat(saved.getId()).isEqualTo(1000L);

        when(operatorShiftRepository.findById(1000L)).thenReturn(Optional.of(saved));
        operatorShiftService.unassign(1000L);
        verify(operatorShiftRepository).save(argThat(s -> s.getUnassignedAt() != null));
    }
}