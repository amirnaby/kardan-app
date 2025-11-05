package com.niam.kardan.repository;

import com.niam.kardan.model.PartOperationTask;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartOperationTaskRepository extends JpaRepository<PartOperationTask, Long> {
    // tasks visible to operator via OperatorMachine relationship (dynamic: join)
    @Query("""
               SELECT t FROM PartOperationTask t
               JOIN OperatorMachine om ON om.machine.id = t.machine.id
               WHERE om.operator.id = :operatorId
                 AND t.taskStatus.id = :pendingStatusId
            """)
    List<PartOperationTask> findPendingTasksForOperator(@Param("operatorId") Long operatorId, @Param("pendingStatusId") Long pendingStatusId);

    // find by id with pessimistic lock for claim
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM PartOperationTask t WHERE t.id = :id")
    Optional<PartOperationTask> findByIdForUpdate(@Param("id") Long id);

    // find pending tasks targeting a machine (for reassign/monitor)
    List<PartOperationTask> findByMachineIdAndTaskStatus_Id(Long machineId, Long statusId);

    // find tasks by part_operation
    List<PartOperationTask> findByPartOperationId(Long partOperationId);

    List<PartOperationTask> findByParentTaskId(Long parentTaskId);
}