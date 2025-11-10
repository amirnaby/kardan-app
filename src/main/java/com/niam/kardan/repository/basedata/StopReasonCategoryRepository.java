package com.niam.kardan.repository.basedata;

import com.niam.kardan.model.basedata.StopReasonCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopReasonCategoryRepository extends JpaRepository<StopReasonCategory, Long> {
}