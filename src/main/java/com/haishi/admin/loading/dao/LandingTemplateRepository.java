package com.haishi.admin.loading.dao;

import com.haishi.admin.loading.entity.LandingTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LandingTemplateRepository extends PagingAndSortingRepository<LandingTemplate, Long>, CrudRepository<LandingTemplate, Long> {
}