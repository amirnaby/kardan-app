package com.niam.kardan.repository.basedata;

import com.niam.kardan.model.basedata.PartOperationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartOperationStatusRepository extends JpaRepository<PartOperationStatus, Long> {
}