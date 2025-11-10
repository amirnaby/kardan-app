package com.niam.kardan.service.basedata;

import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.basedata.ProjectStatus;
import com.niam.kardan.repository.basedata.ProjectStatusRepository;
import com.niam.kardan.service.BaseDataService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "ProjectStatus")
public class ProjectStatusService extends BaseDataService<ProjectStatus> {

    public ProjectStatusService(ProjectStatusRepository repository, MessageUtil messageUtil) {
        super(repository, messageUtil);
    }

    @Override
    protected String getEntityName() {
        return "ProjectStatus";
    }

    @Override
    @CacheEvict(allEntries = true)
    public ProjectStatus create(ProjectStatus entity) {
        return super.create(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ProjectStatus update(Long id, ProjectStatus entity) {
        return super.update(id, entity);
    }

    @Override
    @Cacheable(key = "#id")
    public ProjectStatus getById(Long id) {
        return super.getById(id);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<ProjectStatus> getAll() {
        return super.getAll();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }
}