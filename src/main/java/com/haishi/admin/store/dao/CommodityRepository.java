package com.haishi.admin.store.dao;

import com.haishi.admin.store.entity.Commodity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommodityRepository extends PagingAndSortingRepository<Commodity, Long>, CrudRepository<Commodity, Long> {
}