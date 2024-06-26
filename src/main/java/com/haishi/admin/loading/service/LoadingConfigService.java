package com.haishi.admin.loading.service;

import com.haishi.admin.loading.dto.LoadingConfigQueryDTO;
import com.haishi.admin.loading.entity.QLoadingConfig;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.loading.dao.LoadingConfigRepository;
import com.haishi.admin.loading.entity.LoadingConfig;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadingConfigService {
    private final LoadingConfigRepository loadingConfigRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public LoadingConfig getById(Long id) {
        return loadingConfigRepository.findById(id).orElse(null);
    }

    private JPAQuery<LoadingConfig> buildQuery(LoadingConfigQueryDTO queryDTO) {
        JPAQuery<LoadingConfig> query = jpaQueryFactory.selectFrom(QLoadingConfig.loadingConfig);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<LoadingConfig> list(LoadingConfigQueryDTO queryDTO) {
        JPAQuery<LoadingConfig> query = buildQuery(queryDTO);
        return query.fetch();
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PageDTO<LoadingConfig> page(LoadingConfigQueryDTO queryDTO) {
        JPAQuery<LoadingConfig> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<LoadingConfig> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    @Caching(put = {
            @CachePut(value = "loadingConfig", key = "#loadingConfig.domain + #loadingConfig.path")
    })
    public LoadingConfig save(LoadingConfig loadingConfig) {
        return loadingConfigRepository.save(loadingConfig);
    }

    @CacheEvict(value = "loadingConfig", key = "#id")
    public boolean delete(Long id) {
        loadingConfigRepository.deleteById(id);
        return true;
    }

    @Cacheable(value = "loadingConfig", key = "#domain + #path")
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public LoadingConfig getByPath(String domain, String path) {
        LoadingConfig loadingConfig = jpaQueryFactory.selectFrom(QLoadingConfig.loadingConfig)
                .where(
                        QLoadingConfig.loadingConfig.domain.eq(domain),
                        QLoadingConfig.loadingConfig.path.eq(path)
                )
                .fetchOne();
        log.info("loadingConfig: {}", loadingConfig);
        return loadingConfig;
    }
}