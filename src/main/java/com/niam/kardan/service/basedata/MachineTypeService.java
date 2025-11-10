package com.niam.kardan.service.basedata;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.basedata.MachineType;
import com.niam.kardan.repository.basedata.MachineTypeRepository;
import com.niam.kardan.service.BaseDataService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "MachineType")
public class MachineTypeService extends BaseDataService<MachineType> {

    public MachineTypeService(MachineTypeRepository repository, MessageUtil messageUtil) {
        super(repository, messageUtil);
    }

    @Override
    protected String getEntityName() {
        return "MachineType";
    }

    @Override
    @CacheEvict(allEntries = true)
    public MachineType create(MachineType entity) {
        return super.create(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public MachineType update(Long id, MachineType entity) {
        return super.update(id, entity);
    }

    @Override
    @Cacheable(key = "#id")
    public MachineType getById(Long id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<MachineType> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }
}