package com.haishi.admin.system.dao;

import com.haishi.admin.system.entity.SystemConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemConfigRepository extends PagingAndSortingRepository<SystemConfig, Long>, CrudRepository<SystemConfig, Long> {
}