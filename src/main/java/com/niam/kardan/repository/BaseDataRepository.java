package com.niam.kardan.repository;

import com.niam.kardan.model.basedata.BaseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseDataRepository<T extends BaseData> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    Optional<T> findByCode(String code);

    boolean existsByCode(String code);
}