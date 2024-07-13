package com.haishi.admin.store.dao;

import com.haishi.admin.store.entity.CommodityGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommodityGroupRepository extends PagingAndSortingRepository<CommodityGroup, Long>, CrudRepository<CommodityGroup, Long> {
}