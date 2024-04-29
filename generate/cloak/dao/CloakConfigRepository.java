package com.haishi.admin.cloak.dao;

import com.haishi.admin.cloak.entity.CloakConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CloakConfigRepository extends PagingAndSortingRepository<CloakConfig, Long>, CrudRepository<CloakConfig, Long> {
}