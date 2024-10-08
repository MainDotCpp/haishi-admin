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
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
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
    private final RedissonClient redissonClient;

    public CloakLog getById(Long id) {
        return cloakLogRepository.findById(id).orElse(null);
    }

    public PageDTO<CloakLog> page(CloakLogQueryDTO queryDTO) {
        JPAQuery<CloakLog> query = jpaQueryFactory.selectFrom(QCloakLog.cloakLog);
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (queryDTO.getConfigId() != null) predicates.add(QCloakLog.cloakLog.configId.eq(queryDTO.getConfigId()));
        if (queryDTO.getScene() != null) predicates.add(QCloakLog.cloakLog.Scene.eq(queryDTO.getScene()));
        if (queryDTO.getRelatedId() != null) predicates.add(QCloakLog.cloakLog.relatedId.eq(queryDTO.getRelatedId()));
        if (queryDTO.getStatus() != null) predicates.add(QCloakLog.cloakLog.status.eq(queryDTO.getStatus()));
        if (queryDTO.getIsCrawler() != null) predicates.add(QCloakLog.cloakLog.isCrawler.eq(queryDTO.getIsCrawler()));
        if (queryDTO.getIsMobile() != null) predicates.add(QCloakLog.cloakLog.isMobile.eq(queryDTO.getIsMobile()));
        if (queryDTO.getIsProxy() != null) predicates.add(QCloakLog.cloakLog.isProxy.eq(queryDTO.getIsProxy()));
        if (queryDTO.getCountryCode() != null)
            predicates.add(QCloakLog.cloakLog.countryCode.eq(queryDTO.getCountryCode()));

        query.orderBy(QCloakLog.cloakLog.id.desc());
        query.where(predicates.toArray(Predicate[]::new));

        queryDTO.setTotal(query.fetchCount());
        List<CloakLog> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        // 检测是否是黑名单ip
        var blacklistIp = redissonClient.getBloomFilter("blacklistIp");
        data.forEach(cloakLog -> {
            cloakLog.setInBlacklist(blacklistIp.contains(cloakLog.getIp()));
        });

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