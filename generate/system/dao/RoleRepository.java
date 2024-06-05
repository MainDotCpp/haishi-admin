package com.haishi.admin.system.dao;

import com.haishi.admin.system.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long>, CrudRepository<Role, Long> {
}