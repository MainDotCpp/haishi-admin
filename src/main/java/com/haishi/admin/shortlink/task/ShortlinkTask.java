package com.haishi.admin.shortlink.task;

import com.haishi.admin.cloak.entity.QCloakLog;
import com.haishi.admin.cloak.enums.CheckStatus;
import com.haishi.admin.cloak.enums.CloakScene;
import com.haishi.admin.shortlink.entity.QShortLinkConfig;
import com.haishi.admin.shortlink.entity.ShortLinkConfig;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ShortlinkTask {
    private final JPAQueryFactory jpaQueryFactory;


    @Scheduled(cron = "0 0/20 * * * ?")
    @Transactional(rollbackOn = Exception.class)
    public void CountPv() {
        log.info("开始统计短链接的pv和uv");
        QCloakLog cloakLog = QCloakLog.cloakLog;
        QShortLinkConfig shortLinkConfig = QShortLinkConfig.shortLinkConfig;

        // 查询所有的短链接配置 TODO: 后面优化为近期有访问的配置
        List<ShortLinkConfig> shortLinkConfigs = jpaQueryFactory.selectFrom(shortLinkConfig).fetch();

        for (ShortLinkConfig linkConfig : shortLinkConfigs) {
            Long linkId = linkConfig.getId();
            // 查询pv
            Predicate[] predicates = {
                    cloakLog.relatedId.eq(linkId),
                    cloakLog.Scene.eq(CloakScene.SHORT_LINK),
                    cloakLog.isCrawler.eq(false),
                    cloakLog.status.eq(CheckStatus.PERMIT)
            };
            Long pv = jpaQueryFactory.select(cloakLog.count())
                    .from(cloakLog)
                    .where(predicates).fetchOne();
            // 查询uv
            Long uv = jpaQueryFactory.select(cloakLog.ip.countDistinct())
                    .from(cloakLog)
                    .where(predicates).fetchOne();
            // 更新pv和uv
            jpaQueryFactory.update(shortLinkConfig)
                    .set(shortLinkConfig.pv, pv.intValue())
                    .set(shortLinkConfig.uv, uv.intValue())
                    .where(shortLinkConfig.id.eq(linkId))
                    .execute();
        }
        log.info("统计短链接的pv和uv结束");

    }
}
