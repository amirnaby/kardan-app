package com.niam.kardan.service.basedata;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.basedata.MachineStatus;
import com.niam.kardan.repository.basedata.MachineStatusRepository;
import com.niam.kardan.service.BaseDataService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "MachineStatus")
public class MachineStatusService extends BaseDataService<MachineStatus> {

    public MachineStatusService(MachineStatusRepository repository, MessageUtil messageUtil) {
        super(repository, messageUtil);
    }

    @Override
    protected String getEntityName() {
        return "MachineStatus";
    }

    @Override
    @CacheEvict(allEntries = true)
    public MachineStatus create(MachineStatus entity) {
        return super.create(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public MachineStatus update(Long id, MachineStatus entity) {
        return super.update(id, entity);
    }

    @Override
    @Cacheable(key = "#id")
    public MachineStatus getById(Long id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<MachineStatus> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }
}