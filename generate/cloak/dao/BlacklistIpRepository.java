package com.haishi.admin.cloak.dao;

import com.haishi.admin.cloak.entity.BlacklistIp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlacklistIpRepository extends PagingAndSortingRepository<BlacklistIp, Long>, CrudRepository<BlacklistIp, Long> {
}