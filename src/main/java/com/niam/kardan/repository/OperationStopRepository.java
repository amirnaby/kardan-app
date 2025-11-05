package com.niam.kardan.repository;

import com.niam.kardan.model.OperationStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationStopRepository extends JpaRepository<OperationStop, Long> {
    // find stops by execution
    List<OperationStop> findByOperationExecutionId(Long executionId);

    // find open stops (ended_at is null) for a machine or execution
    List<OperationStop> findByOperationExecutionMachineIdAndEndedAtIsNull(Long machineId);
}