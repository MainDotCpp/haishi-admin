package com.haishi.admin.resource.dao;

import com.haishi.admin.resource.entity.DomainAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DomainAccountRepository extends PagingAndSortingRepository<DomainAccount, Long>, CrudRepository<DomainAccount, Long> {
}