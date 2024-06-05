package com.haishi.admin.system.dao;

import com.haishi.admin.system.entity.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long>, CrudRepository<Permission, Long> {
}