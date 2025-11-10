package com.niam.kardan.service;

import com.niam.common.exception.EntityNotFoundException;
import com.niam.common.exception.ResultResponseStatus;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.Machine;
import com.niam.kardan.repository.MachineRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MachineService {
    private final MachineRepository machineRepository;
    private final MessageUtil messageUtil;

    @Lazy
    @Autowired
    private MachineService self;

    @Transactional
    @CacheEvict(value = {"machines", "machine"}, allEntries = true)
    public Machine create(Machine machine) {
        return machineRepository.save(machine);
    }

    @Transactional
    @CacheEvict(value = {"machines", "machine"}, allEntries = true)
    public Machine update(Long id, Machine updated) {
        Machine existing = self.getById(id);
        BeanUtils.copyProperties(updated, existing, "id", "createdAt", "updatedAt");
        return machineRepository.save(existing);
    }

    @Cacheable(value = "machine", key = "#id")
    public Machine getById(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                                ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                                messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(),
                                        "Machine")));
    }

    @Cacheable(value = "machines")
    public List<Machine> getAll() {
        return machineRepository.findAll();
    }

    @Transactional
    @CacheEvict(value = {"machines", "machine"}, allEntries = true)
    public void delete(Long id) {
        Machine machine = self.getById(id);
        try {
            machineRepository.delete(machine);
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistsException(
                    messageUtil.getMessage(ResultResponseStatus.ENTITY_HAS_DEPENDENCIES.getDescription(), "Machine"));
        }
    }

    @Transactional
    @CacheEvict(value = {"machines", "machine"}, allEntries = true)
    public Machine updateStatus(Long id, String newStatusCode) {
        Machine machine = self.getById(id);
        machine.getMachineStatus().setCode(newStatusCode);
        return machineRepository.save(machine);
    }
}