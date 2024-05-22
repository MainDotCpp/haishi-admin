package com.haishi.admin.shortlink.dao;

import com.haishi.admin.shortlink.entity.ShortLinkGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShortLinkGroupRepository extends PagingAndSortingRepository<ShortLinkGroup, Long>, CrudRepository<ShortLinkGroup, Long> {
}