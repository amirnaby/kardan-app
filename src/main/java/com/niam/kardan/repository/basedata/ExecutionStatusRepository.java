package com.niam.kardan.repository.basedata;

import com.niam.kardan.model.basedata.ExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionStatusRepository extends JpaRepository<ExecutionStatus, Long> {
}