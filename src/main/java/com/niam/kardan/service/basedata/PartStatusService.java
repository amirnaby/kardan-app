package com.niam.kardan.service.basedata;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.basedata.PartStatus;
import com.niam.kardan.repository.basedata.PartStatusRepository;
import com.niam.kardan.service.BaseDataService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "PartStatus")
public class PartStatusService extends BaseDataService<PartStatus> {

    public PartStatusService(PartStatusRepository repository, MessageUtil messageUtil) {
        super(repository, messageUtil);
    }

    @Override
    protected String getEntityName() {
        return "PartStatus";
    }

    @Override
    @CacheEvict(allEntries = true)
    public PartStatus create(PartStatus entity) {
        return super.create(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public PartStatus update(Long id, PartStatus entity) {
        return super.update(id, entity);
    }

    @Override
    @Cacheable(key = "#id")
    public PartStatus getById(Long id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<PartStatus> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }
}