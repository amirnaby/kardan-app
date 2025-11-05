package com.niam.kardan.repository;

import com.niam.kardan.model.OperatorShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperatorShiftRepository extends JpaRepository<OperatorShift, Long> {
    Optional<OperatorShift> findActiveByOperatorId(Long operatorId);

    List<OperatorShift> findByOperatorIdAndUnassignedAtIsNull(Long operatorId);

    List<OperatorShift> findByShiftIdAndUnassignedAtIsNull(Long shiftId);
}