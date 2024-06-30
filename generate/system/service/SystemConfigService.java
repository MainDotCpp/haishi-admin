package com.haishi.admin.system.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.system.dao.SystemConfigRepository;
import com.haishi.admin.system.dto.SystemConfigDTO;
import com.haishi.admin.system.entity.QSystemConfig;
import com.haishi.admin.system.entity.SystemConfig;
import com.haishi.admin.system.mapper.SystemConfigMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for {@link SystemConfig}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfigService {
    private final SystemConfigRepository systemConfigRepository;
    private final SystemConfigMapper systemConfigMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取系统设置
     *
     * @param id ID
     * @return {@link SystemConfigDTO}
     */
    public SystemConfigDTO getById(Long id) {
        return systemConfigMapper.toSystemConfigDTO(systemConfigRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<SystemConfig>}
     */
    private JPAQuery<SystemConfig> buildQuery(SystemConfigDTO dto) {
        QSystemConfig qsystemConfig = QSystemConfig.systemConfig;
        JPAQuery<SystemConfig> query = jpaQueryFactory
                .selectFrom(qsystemConfig);
        query.where(new Predicate[]{
                dto.getId() != null ? qsystemConfig.id.eq(dto.getId()) : null,
        });
        query.orderBy(qsystemConfig.id.desc());
        return query;
    }

    /**
     * 系统设置列表
     *
     * @param dto 查询条件
     * @return {@link List<SystemConfigDTO>}
     */
    public List<SystemConfigDTO> list(SystemConfigDTO dto) {
        JPAQuery<SystemConfig> query = buildQuery(dto);
        return systemConfigMapper.toSystemConfigDTOList(query.fetch());
    }

    /**
     * 分页查询系统设置
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<SystemConfigDTO>}
     */
    public PageDTO<SystemConfigDTO> page(SystemConfigDTO dto, Integer current, Integer pageSize) {
        JPAQuery<SystemConfig> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<SystemConfig> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, systemConfigMapper.toSystemConfigDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存系统设置
     *
     * @param dto {@link SystemConfigDTO}
     * @return {@link SystemConfigDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public SystemConfigDTO save(SystemConfigDTO dto) {
        SystemConfig systemConfig = new SystemConfig();
        if (dto.getId() != null)
            systemConfig = systemConfigRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:系统设置不存在"));
        systemConfigMapper.partialUpdate(dto, systemConfig);
        return systemConfigMapper.toSystemConfigDTO(systemConfigRepository.save(systemConfig));
    }

    /**
     * 通过ID删除系统设置
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        systemConfigRepository.deleteById(id);
        return true;
    }
}