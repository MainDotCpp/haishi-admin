package com.haishi.admin.cloak.dao;

import com.haishi.admin.cloak.entity.CloakConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CloakConfigRepository extends PagingAndSortingRepository<CloakConfig, UUID>, CrudRepository<CloakConfig, UUID> {
}