package com.haishi.admin.store.dao;

import com.haishi.admin.store.entity.CommodityItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommodityItemRepository extends PagingAndSortingRepository<CommodityItem, Long>, CrudRepository<CommodityItem, Long> {
}