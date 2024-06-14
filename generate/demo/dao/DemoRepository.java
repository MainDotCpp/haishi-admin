package com.haishi.admin.demo.dao;

import com.haishi.admin.demo.entity.Demo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DemoRepository extends PagingAndSortingRepository<Demo, Long>, CrudRepository<Demo, Long> {
}