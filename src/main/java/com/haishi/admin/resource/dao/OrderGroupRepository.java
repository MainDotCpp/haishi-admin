package com.haishi.admin.resource.dao;

import com.haishi.admin.resource.entity.OrderGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderGroupRepository extends PagingAndSortingRepository<OrderGroup, Long>, CrudRepository<OrderGroup, Long> {
}