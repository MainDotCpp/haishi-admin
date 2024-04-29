package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dto.CloakLogQueryDTO;
import com.haishi.admin.cloak.entity.QCloakLog;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.cloak.dao.CloakLogRepository;
import com.haishi.admin.cloak.entity.CloakLog;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class CloakLogService {
    private final CloakLogRepository cloakLogRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public CloakLog getById(Long id) {
        return cloakLogRepository.findById(id).orElse(null);
    }

    public PageDTO<CloakLog> page(CloakLogQueryDTO queryDTO) {
        JPAQuery<CloakLog> query = jpaQueryFactory.selectFrom(QCloakLog.cloakLog);
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (queryDTO.getConfigId() != null) {
            predicates.add(QCloakLog.cloakLog.configId.eq(queryDTO.getConfigId()));
        }
        query.where(predicates.toArray(Predicate[]::new));

        queryDTO.setTotal(query.fetchCount());
        List<CloakLog> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public CloakLog save(CloakLog cloakLog) {
        return cloakLogRepository.save(cloakLog);
    }

    public boolean delete(Long id) {
        cloakLogRepository.deleteById(id);
        return true;
    }
}