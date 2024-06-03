package com.haishi.admin.system.service;

import com.haishi.admin.system.dto.PermissionQueryDTO;
import com.haishi.admin.system.entity.QPermission;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dao.PermissionRepository;
import com.haishi.admin.system.entity.Permission;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Permission getById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    private JPAQuery<Permission> buildQuery(PermissionQueryDTO queryDTO) {
        JPAQuery<Permission> query = jpaQueryFactory.selectFrom(QPermission.permission);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    public List<Permission> list(PermissionQueryDTO queryDTO) {
        JPAQuery<Permission> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<Permission> page(PermissionQueryDTO queryDTO) {
        JPAQuery<Permission> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<Permission> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    public boolean delete(Long id) {
        permissionRepository.deleteById(id);
        return true;
    }
}