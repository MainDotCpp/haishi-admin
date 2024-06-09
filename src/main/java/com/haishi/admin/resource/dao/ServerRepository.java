package com.haishi.admin.resource.dao;

import com.haishi.admin.resource.entity.Server;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ServerRepository extends PagingAndSortingRepository<Server, Long>, CrudRepository<Server, Long> {
}