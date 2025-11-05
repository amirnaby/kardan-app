package com.niam.kardan.repository;

import com.niam.kardan.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    Optional<Machine> findByCode(String code);

    // find idle machines of same type
    List<Machine> findByMachineTypeIdAndMachineStatus_Id(Long machineTypeId, Long idleStatusId);
}