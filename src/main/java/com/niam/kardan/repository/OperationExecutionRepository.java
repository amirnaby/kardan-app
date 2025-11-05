package com.niam.kardan.repository;

import com.niam.kardan.model.OperationExecution;
import com.niam.kardan.model.basedata.ExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationExecutionRepository extends JpaRepository<OperationExecution, Long> {
    // find current running execution for a given part_operation
    @Query("SELECT p FROM OperationExecution p WHERE p.partOperation.id = :partOperationId AND p.executionStatus.id = :startedStatusId")
    Optional<OperationExecution> findCurrentByPartOperation(@Param("partOperationId") Long partOperationId, @Param("startedStatusId") Long startedStatusId);

    // find executions by operator during active shift, etc.
    List<OperationExecution> findByOperatorIdAndExecutionStatus(Long operatorId, ExecutionStatus statusId);

    boolean existsByMachineIdAndExecutionStatusId(Long machineId, Long startedStatusId);
}