package com.haishi.admin.cloak.dao;

import com.haishi.admin.cloak.entity.CloakLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CloakLogRepository extends PagingAndSortingRepository<CloakLog, Long>, CrudRepository<CloakLog, Long> {
}