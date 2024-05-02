package com.haishi.admin.loading.dao;

import com.haishi.admin.loading.entity.LoadingConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LoadingConfigRepository extends PagingAndSortingRepository<LoadingConfig, Long>, CrudRepository<LoadingConfig, Long> {
}