package com.niam.kardan.repository;

import com.niam.kardan.model.OperatorShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorShiftRepository extends JpaRepository<OperatorShift, Long> {
    boolean existsByOperatorIdAndUnassignedAtIsNull(Long operatorId);

    boolean existsByShiftIdAndUnassignedAtIsNull(Long shiftId);

    List<OperatorShift> findByOperatorIdAndUnassignedAtIsNull(Long operatorId);
}