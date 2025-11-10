package com.niam.kardan.service.basedata;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.basedata.StopReasonCategory;
import com.niam.kardan.repository.basedata.StopReasonCategoryRepository;
import com.niam.kardan.service.BaseDataService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "StopReasonCategory")
public class StopReasonCategoryService extends BaseDataService<StopReasonCategory> {

    public StopReasonCategoryService(StopReasonCategoryRepository repository, MessageUtil messageUtil) {
        super(repository, messageUtil);
    }

    @Override
    protected String getEntityName() {
        return "StopReasonCategory";
    }

    @Override
    @CacheEvict(allEntries = true)
    public StopReasonCategory create(StopReasonCategory entity) {
        return super.create(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public StopReasonCategory update(Long id, StopReasonCategory entity) {
        return super.update(id, entity);
    }

    @Override
    @Cacheable(key = "#id")
    public StopReasonCategory getById(Long id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<StopReasonCategory> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }
}