package com.haishi.admin.store.dao;

import com.haishi.admin.store.entity.CommodityOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommodityOrderRepository extends PagingAndSortingRepository<CommodityOrder, Long>, CrudRepository<CommodityOrder, Long> {
}