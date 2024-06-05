package com.haishi.admin.system.dao;

import com.haishi.admin.system.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
    boolean existsByUsername(String username);

    User findByUsername(@NonNull String username);
}