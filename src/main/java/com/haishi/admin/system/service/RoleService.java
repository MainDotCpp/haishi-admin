package com.haishi.admin.system.service;

import com.haishi.admin.system.dto.RoleQueryDTO;
import com.haishi.admin.system.entity.QRole;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dao.RoleRepository;
import com.haishi.admin.system.entity.Role;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    private JPAQuery<Role> buildQuery(RoleQueryDTO queryDTO) {
        JPAQuery<Role> query = jpaQueryFactory.selectFrom(QRole.role);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    public List<Role> list(RoleQueryDTO queryDTO) {
        JPAQuery<Role> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<Role> page(RoleQueryDTO queryDTO) {
        JPAQuery<Role> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<Role> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    @CacheEvict(value = "session", allEntries = true)
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public boolean delete(Long id) {
        roleRepository.deleteById(id);
        return true;
    }

    public List<Role> getByIds(List<Long> roleIds) {
        return jpaQueryFactory.selectFrom(QRole.role).where(QRole.role.id.in(roleIds)).fetch();
    }
}