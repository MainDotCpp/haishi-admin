package com.haishi.admin.cloak.service;

import com.haishi.admin.cloak.dto.BlacklistIpQueryDTO;
import com.haishi.admin.cloak.entity.QBlacklistIp;
import com.haishi.admin.common.config.RedisConfig;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.cloak.dao.BlacklistIpRepository;
import com.haishi.admin.cloak.entity.BlacklistIp;
import com.haishi.admin.system.entity.QSystemConfig;
import com.haishi.admin.system.entity.SystemConfig;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlacklistIpService {
    private final BlacklistIpRepository blacklistIpRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final RedissonClient redissonClient;

    public BlacklistIp getById(Long id) {
        return blacklistIpRepository.findById(id).orElse(null);
    }

    private JPAQuery<BlacklistIp> buildQuery(BlacklistIpQueryDTO queryDTO) {
        JPAQuery<BlacklistIp> query = jpaQueryFactory.selectFrom(QBlacklistIp.blacklistIp);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    public List<BlacklistIp> list(BlacklistIpQueryDTO queryDTO) {
        JPAQuery<BlacklistIp> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<BlacklistIp> page(BlacklistIpQueryDTO queryDTO) {
        JPAQuery<BlacklistIp> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<BlacklistIp> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public BlacklistIp save(BlacklistIp blacklistIp) {
        // 添加到布隆过滤器中
        redissonClient.getBloomFilter("blacklistIp").add(blacklistIp.getIp().toString());
        return blacklistIpRepository.save(blacklistIp);
    }

    public boolean delete(Long id) {
        blacklistIpRepository.deleteById(id);
        return true;
    }

    public String getBlacklistIp() {
        var systemConfig = jpaQueryFactory.selectFrom(QSystemConfig.systemConfig)
                .where(QSystemConfig.systemConfig.configKey.eq("cloak.blacklistIp.init"))
                .fetchFirst();
        return systemConfig.getConfigValue();
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean setBlacklistIp(String blacklistIp) {
        var systemConfig = jpaQueryFactory.selectFrom(QSystemConfig.systemConfig)
                .where(QSystemConfig.systemConfig.configKey.eq("cloak.blacklistIp.init"))
                .fetchFirst();
        systemConfig.setConfigValue(blacklistIp);
        return true;
    }
}