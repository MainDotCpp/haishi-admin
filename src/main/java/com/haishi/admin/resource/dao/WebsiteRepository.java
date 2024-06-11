package com.haishi.admin.resource.dao;

import com.haishi.admin.resource.entity.Website;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WebsiteRepository extends PagingAndSortingRepository<Website, Long>, CrudRepository<Website, Long> {
}