package com.niam.kardan.repository;

import com.niam.kardan.model.OperatorMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorMachineRepository extends JpaRepository<OperatorMachine, Long> {
    List<OperatorMachine> findByOperatorIdAndUnassignedAtIsNull(Long operatorId);

    // find operators that can operate a machine
    List<OperatorMachine> findByMachineIdAndUnassignedAtIsNull(Long machineId);

    boolean existsByOperatorIdAndMachineIdAndUnassignedAtIsNull(Long operatorId, Long machineId);
}