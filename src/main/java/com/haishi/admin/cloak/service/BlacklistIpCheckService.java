package com.haishi.admin.cloak.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.net.Ipv4Util;
import com.haishi.admin.cloak.dao.BlacklistIpRepository;
import com.haishi.admin.cloak.entity.*;
import com.haishi.admin.cloak.enums.CheckStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlacklistIpCheckService extends CloakCheckHandleIntercept {
    private final BlacklistIpRepository blacklistIpRepository;

    private final RedissonClient redissonClient;
    private final JPAQueryFactory jpaQueryFactory;
    private final BlacklistIpService blacklistIpService;

    @Override
    String checkName() {
        return "黑名单IP检查";
    }

    @Override
    protected boolean isEnable(CloakLog cloakLog, CloakConfig cloakConfig) {
        return cloakConfig.getEnableBlacklistIpDetection() || cloakConfig.getEnableBlacklistIpCollection();
    }

    @PostConstruct
    public void init() {

    }

    public Boolean initFilter() {
        TimeInterval timer = DateUtil.timer();
        log.info("黑名单IP检查，初始化布隆过滤器");
        // 初始化布隆过滤器
        RBloomFilter<Object> visitorFilter = redissonClient.getBloomFilter("visitorIp");
        RBloomFilter<Object> blacklistIpFilter = redissonClient.getBloomFilter("blacklistIp");
        visitorFilter.tryInit(1000000, 0.03);
        blacklistIpFilter.tryInit(1000000, 0.03);

        QBlacklistIp blacklistIp = QBlacklistIp.blacklistIp;
        QCloakLog cloakLog = QCloakLog.cloakLog;
        jpaQueryFactory.select(cloakLog.ip).from(cloakLog).fetch().forEach(i -> {
            log.info("黑名单IP检查，初始化布隆过滤器，添加访客IP：{}", i);
            visitorFilter.add(i);
        });
        jpaQueryFactory.select(blacklistIp.ip).from(blacklistIp).fetch().forEach(blacklistIpFilter::add);
        log.info("黑名单IP检查，初始化布隆过滤器完成，耗时：{}", timer.intervalMs());
        return true;
    }

    boolean collectBlacklistIp(RBloomFilter<Object> visitorFilter, CloakLog cloakLog) {
        if (visitorFilter.contains(cloakLog.getIp())) {
            log.info("黑名单IP检查，添加黑名单IP：{}", cloakLog.getIp());
            BlacklistIp entity = new BlacklistIp();
            try {
                entity.setIp(InetAddress.getByName(cloakLog.getIp()));
            } catch (UnknownHostException e) {
                log.error("黑名单IP转换失败", e);
            }
            blacklistIpRepository.save(entity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    CheckStatus check(CloakLog cloakLog, CloakConfig cloakConfig) {
        RBloomFilter<Object> visitorFilter = redissonClient.getBloomFilter("visitorIp");
        RBloomFilter<Object> blacklistIpFilter = redissonClient.getBloomFilter("blacklistIp");

        boolean flag = false;
        long total = visitorFilter.count();
        log.info("黑名单IP检查，访客IP数量：{}", total);
        if (cloakConfig.getEnableBlacklistIpDetection() && blacklistIpFilter.contains(cloakLog.getIp())) {
            log.info("黑名单IP检查，拦截IP：{}", cloakLog.getIp());
            flag = true;
        }
        if (cloakConfig.getEnableBlacklistIpCollection()) {
            flag = collectBlacklistIp(visitorFilter, cloakLog);
        }

        visitorFilter.add(cloakLog.getIp());
        return flag ? CheckStatus.FORBID_BY_BLACKLIST_IP : null;
    }
}
