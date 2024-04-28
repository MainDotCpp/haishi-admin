package com.haishi.admin.shortlink.dao;

import com.haishi.admin.shortlink.entity.ShortLinkConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShortLinkConfigRepository extends PagingAndSortingRepository<ShortLinkConfig, Long>, CrudRepository<ShortLinkConfig, Long> {
    ShortLinkConfig findByKey(String key);
}