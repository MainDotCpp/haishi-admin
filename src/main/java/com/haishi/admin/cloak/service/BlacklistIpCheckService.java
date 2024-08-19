package com.haishi.admin.cloak.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.haishi.admin.cloak.dao.BlacklistIpRepository;
import com.haishi.admin.cloak.entity.*;
import com.haishi.admin.cloak.enums.CheckStatus;
import com.haishi.admin.system.entity.QSystemConfig;
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
        RBloomFilter<Object> blacklistIpFilter = redissonClient.getBloomFilter("blacklistIp");
        if (blacklistIpFilter.isExists()) {
            return;
        }
        // 加载黑名单
        var blacklistStr = jpaQueryFactory
                .selectFrom(QSystemConfig.systemConfig)
                .where(QSystemConfig.systemConfig.configKey.eq("cloak.blacklistIp.init"))
                .fetchFirst();
        var blacklist = blacklistStr.getConfigValue().split("\n");
        for (String ip : blacklist) {
            log.info("黑名单IP检查，初始化布隆过滤器，添加黑名单IP：{}", ip);
            blacklistIpFilter.add(ip);
        }
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

    boolean collectBlacklistIp(RBloomFilter<Object> visitorFilter, RBloomFilter<Object> blacklistFilter, CloakLog cloakLog) {
        if (blacklistFilter.contains(cloakLog.getIp())) {
            return true;
        }
        if (visitorFilter.contains(cloakLog.getIp())) {
            log.info("黑名单IP检查，添加黑名单IP：{}", cloakLog.getIp());
            BlacklistIp entity = new BlacklistIp();
            try {
                entity.setIp(InetAddress.getByName(cloakLog.getIp()));
            } catch (UnknownHostException e) {
                log.error("黑名单IP转换失败", e);
            }
            blacklistIpRepository.save(entity);
            blacklistFilter.add(cloakLog.getIp());
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
        if (blacklistIpFilter.contains(cloakLog.getIp())) {
            log.info("黑名单IP命中：{}", cloakLog.getIp());
            flag = true;
        }
        if (!flag && cloakConfig.getEnableBlacklistIpCollection()) {
            flag = collectBlacklistIp(visitorFilter, blacklistIpFilter, cloakLog);
        }

        visitorFilter.add(cloakLog.getIp());
        if (!cloakConfig.getEnableBlacklistIpDetection()) return null;
        return flag ? CheckStatus.FORBID_BY_BLACKLIST_IP : null;
    }
}
