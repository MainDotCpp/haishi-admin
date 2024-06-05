package com.haishi.admin.system.dao;

import com.haishi.admin.system.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
}