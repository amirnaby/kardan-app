package com.niam.kardan.repository;

import com.niam.kardan.model.PartOperation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartOperationRepository extends JpaRepository<PartOperation, Long> {
    // find next operation by part and sequence
    Optional<PartOperation> findByPartIdAndSequence(Long partId, Integer sequence);

    // find operations for a part ordered by sequence (useful to create tasks)
    List<PartOperation> findByPartIdOrderBySequenceAsc(Long partId);

    // find the last completed operation for a part
    @Query("SELECT p FROM PartOperation p WHERE p.part.id = :partId AND p.partOperationStatus.id = :completedStatusId ORDER BY p.sequence DESC")
    List<PartOperation> findCompletedByPart(@Param("partId") Long partId, @Param("completedStatusId") Long completedStatusId);

    // optional: lockable load (if you need)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PartOperation p WHERE p.id = :id")
    Optional<PartOperation> findByIdForUpdate(@Param("id") Long id);
}