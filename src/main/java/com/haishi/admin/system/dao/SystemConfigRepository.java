package com.haishi.admin.system.dao;

import com.haishi.admin.system.entity.SystemConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface SystemConfigRepository extends PagingAndSortingRepository<SystemConfig, Long>, CrudRepository<SystemConfig, Long> {
    Optional<SystemConfig> findByConfigKey(@NonNull String configKey);
}