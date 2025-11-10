package com.niam.kardan.service;

import com.niam.common.exception.EntityNotFoundException;
import com.niam.common.exception.ResultResponseStatus;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.basedata.BaseData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseDataService<T extends BaseData> {
    protected final JpaRepository<T, Long> repository;
    protected final MessageUtil messageUtil;

    protected abstract String getEntityName();

    @Transactional
    public T create(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public T update(Long id, T updatedEntity) {
        T existing = getById(id);
        BeanUtils.copyProperties(updatedEntity, existing, "id", "createdAt", "updatedAt");
        return repository.save(existing);
    }

    public T getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                                ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                                messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(),
                                        getEntityName())));
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        T existing = getById(id);
        repository.delete(existing);
    }
}