package com.haishi.admin.system.dao;

import com.haishi.admin.system.entity.Dept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeptRepository extends PagingAndSortingRepository<Dept, Long>, CrudRepository<Dept, Long> {
}