package com.haishi.admin.resource.dao;

import com.haishi.admin.resource.entity.Domain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DomainRepository extends PagingAndSortingRepository<Domain, Long>, CrudRepository<Domain, Long> {
}