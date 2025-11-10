package com.niam.kardan.service.basedata;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.basedata.TaskStatus;
import com.niam.kardan.repository.basedata.TaskStatusRepository;
import com.niam.kardan.service.BaseDataService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "TaskStatus")
public class TaskStatusService extends BaseDataService<TaskStatus> {

    public TaskStatusService(TaskStatusRepository repository, MessageUtil messageUtil) {
        super(repository, messageUtil);
    }

    @Override
    protected String getEntityName() {
        return "TaskStatus";
    }

    @Override
    @CacheEvict(allEntries = true)
    public TaskStatus create(TaskStatus entity) {
        return super.create(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public TaskStatus update(Long id, TaskStatus entity) {
        return super.update(id, entity);
    }

    @Override
    @Cacheable(key = "#id")
    public TaskStatus getById(Long id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<TaskStatus> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }
}