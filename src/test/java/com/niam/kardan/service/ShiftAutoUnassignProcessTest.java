package com.niam.kardan.service;

import com.niam.kardan.model.OperatorShift;
import com.niam.kardan.model.Shift;
import com.niam.kardan.repository.OperatorShiftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShiftAutoUnassignProcessTest {
    @Mock
    private OperatorShiftRepository operatorShiftRepository;
    @Mock
    private OperatorShiftService operatorShiftService;

    private Shift shift;

    @BeforeEach
    void init() {
        shift = new Shift();
        shift.setId(3L);
        shift.setEndTime(LocalDateTime.now().minusMinutes(1));
    }

    @Test
    void expiredShifts_shouldTriggerUnassign() {
        OperatorShift active = new OperatorShift();
        active.setId(11L);
        active.setShift(shift);
        active.setUnassignedAt(null);

        when(operatorShiftRepository.findAll()).thenReturn(List.of(active));

        new ShiftScheduler(operatorShiftRepository, operatorShiftService).checkExpiredShifts();

        verify(operatorShiftService, times(1)).unassign(active.getId());
    }

    private record ShiftScheduler(OperatorShiftRepository repo, OperatorShiftService service) {

        void checkExpiredShifts() {
            repo.findAll().stream()
                    .filter(s -> s.getUnassignedAt() == null && s.getShift().getEndTime().isBefore(LocalDateTime.now()))
                    .forEach(s -> service.unassign(s.getId()));
        }
    }
}