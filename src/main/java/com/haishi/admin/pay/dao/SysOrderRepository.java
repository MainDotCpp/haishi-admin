package com.haishi.admin.pay.dao;

import com.haishi.admin.pay.entity.SysOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SysOrderRepository extends PagingAndSortingRepository<SysOrder, Long>, CrudRepository<SysOrder, Long> {
}