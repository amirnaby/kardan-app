package com.niam.kardan.repository.basedata;

import com.niam.kardan.model.basedata.ShiftStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftStatusRepository extends JpaRepository<ShiftStatus, Long> {
}