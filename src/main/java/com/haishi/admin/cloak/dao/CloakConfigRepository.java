package com.haishi.admin.cloak.dao;

import com.haishi.admin.cloak.entity.CloakConfig;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CloakConfigRepository extends CrudRepository<CloakConfig, UUID> {
}