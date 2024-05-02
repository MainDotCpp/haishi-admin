package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dto.CloakConfigQueryDTO;
import com.haishi.admin.cloak.entity.QCloakConfig;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.cloak.dao.CloakConfigRepository;
import com.haishi.admin.cloak.entity.CloakConfig;
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
public class CloakConfigService {
    private final CloakConfigRepository cloakConfigRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public CloakConfig getById(UUID id) {
        return cloakConfigRepository.findById(id).orElse(null);
    }

    private JPAQuery<CloakConfig> buildQuery(CloakConfigQueryDTO queryDTO) {
        JPAQuery<CloakConfig> query = jpaQueryFactory.selectFrom(QCloakConfig.cloakConfig);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    public List<CloakConfig> list(CloakConfigQueryDTO queryDTO) {
        JPAQuery<CloakConfig> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<CloakConfig> page(CloakConfigQueryDTO queryDTO) {
        JPAQuery<CloakConfig> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<CloakConfig> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public CloakConfig save(CloakConfig cloakConfig) {
        return cloakConfigRepository.save(cloakConfig);
    }

    public boolean delete(UUID id) {
        cloakConfigRepository.deleteById(id);
        return true;
    }
}