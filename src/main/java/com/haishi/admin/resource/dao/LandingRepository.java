package com.haishi.admin.resource.dao;

import com.haishi.admin.resource.entity.Landing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LandingRepository extends PagingAndSortingRepository<Landing, Long>, CrudRepository<Landing, Long> {
}