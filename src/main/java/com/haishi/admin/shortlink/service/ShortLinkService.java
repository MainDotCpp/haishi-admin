package com.haishi.admin.shortlink.service;

import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.entity.QCloakConfig;
import com.haishi.admin.cloak.enums.CloakScene;
import com.haishi.admin.cloak.service.CloakService;
import com.haishi.admin.common.ThreadUserinfo;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.common.exception.BizExceptionEnum;
import com.haishi.admin.shortlink.dao.ShortLinkConfigRepository;
import com.haishi.admin.shortlink.dto.ShortLinkConfigQueryDTO;
import com.haishi.admin.shortlink.dto.ShortLinkGroupQueryDTO;
import com.haishi.admin.shortlink.entity.QShortLinkConfig;
import com.haishi.admin.shortlink.entity.QShortLinkGroup;
import com.haishi.admin.shortlink.entity.ShortLinkConfig;
import com.haishi.admin.shortlink.entity.ShortLinkGroup;
import com.haishi.admin.system.constants.PermissionCode;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkService {
    private final ShortLinkConfigRepository shortLinkConfigRepository;
    private final CloakService cloakService;
    private final JPAQueryFactory jpaQueryFactory;

    @Resource
    private ShortLinkService _this;

    public ShortLinkConfig getById(Long id) {
        return shortLinkConfigRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "shortLinkConfig", key = "#key")
    public ShortLinkConfig getByKey(String key) {
        return shortLinkConfigRepository.findByKey(key);
    }

    private JPAQuery<ShortLinkConfig> buildQuery(ShortLinkConfigQueryDTO queryDTO) {
        QShortLinkConfig shortLinkConfig = QShortLinkConfig.shortLinkConfig;
        JPAQuery<ShortLinkConfig> query = jpaQueryFactory.selectFrom(shortLinkConfig);
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (queryDTO.getGroupId() != null) predicates.add(shortLinkConfig.groupId.eq(queryDTO.getGroupId()));
        if (queryDTO.getCreatedBy() != null) predicates.add(shortLinkConfig.createdBy.eq(queryDTO.getCreatedBy()));
        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(shortLinkConfig.id.asc());
        if (!ThreadUserinfo.get().getPermissions().contains(PermissionCode.SHORT_LINK__ALL_DATA)) {
            query.where(shortLinkConfig.createdBy.eq(ThreadUserinfo.get().getId()));
        }
        return query;
    }

    public PageDTO<ShortLinkConfig> getPage(ShortLinkConfigQueryDTO queryDTO) {
        JPAQuery<ShortLinkConfig> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<ShortLinkConfig> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }



    @CachePut(value = "shortLinkConfig", key = "#shortLinkConfig.key")
    public ShortLinkConfig save(ShortLinkConfig shortLinkConfig) {
        return shortLinkConfigRepository.save(shortLinkConfig);
    }

    public boolean delete(Long id) {
        shortLinkConfigRepository.deleteById(id);
        return true;
    }

    @SneakyThrows
    public Object access(String key, HttpServletRequest request, Boolean preview) {
        log.info("短链接访问，key：{}，preview：{}", key, preview);
        if ("favicon.ico".equals(key)) return "404";
        ShortLinkConfig shortLinkConfig = _this.getByKey(key);
        if (shortLinkConfig == null) throw new BizException(BizExceptionEnum.SHORT_LINK_NOT_EXIST);
        if (shortLinkConfig.getCloakId() == null) throw new BizException(BizExceptionEnum.CLOAK_LINK_NOT_CONFIG);

        RedirectView redirectView = new RedirectView(shortLinkConfig.getTargetUrl());
        redirectView.setStatusCode(HttpStatusCode.valueOf(302));
        if (Boolean.TRUE.equals(preview)) return redirectView;

        UUID cloakId = shortLinkConfig.getCloakId();
        CloakCheckResult result = cloakService.check(cloakId.toString(), request, cloakLog -> {
            cloakLog.setScene(CloakScene.SHORT_LINK);
            cloakLog.setRelatedId(shortLinkConfig.getId());
            return cloakLog;
        });
        if (result.getPermit()) return redirectView;
        return "404";
    }
}
