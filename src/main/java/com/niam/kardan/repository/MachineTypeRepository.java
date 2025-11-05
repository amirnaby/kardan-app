package com.niam.kardan.repository;

import com.niam.kardan.model.basedata.MachineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineTypeRepository extends JpaRepository<MachineType, Long> {
}