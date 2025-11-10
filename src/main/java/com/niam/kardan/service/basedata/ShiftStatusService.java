package com.niam.kardan.service.basedata;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.basedata.ShiftStatus;
import com.niam.kardan.repository.basedata.ShiftStatusRepository;
import com.niam.kardan.service.BaseDataService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "ShiftStatus")
public class ShiftStatusService extends BaseDataService<ShiftStatus> {

    public ShiftStatusService(ShiftStatusRepository repository, MessageUtil messageUtil) {
        super(repository, messageUtil);
    }

    @Override
    protected String getEntityName() {
        return "ShiftStatus";
    }

    @Override
    @CacheEvict(allEntries = true)
    public ShiftStatus create(ShiftStatus entity) {
        return super.create(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ShiftStatus update(Long id, ShiftStatus entity) {
        return super.update(id, entity);
    }

    @Override
    @Cacheable(key = "#id")
    public ShiftStatus getById(Long id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<ShiftStatus> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }
}