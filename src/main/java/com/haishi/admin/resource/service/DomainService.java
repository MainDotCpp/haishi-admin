package com.haishi.admin.resource.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.resource.dao.DomainRepository;
import com.haishi.admin.resource.dto.DomainDTO;
import com.haishi.admin.resource.entity.Domain;
import com.haishi.admin.resource.entity.QDomain;
import com.haishi.admin.resource.entity.QServer;
import com.haishi.admin.resource.mapper.DomainMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for {@link Domain}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;
    private final DomainMapper domainMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取域名
     *
     * @param id ID
     * @return {@link DomainDTO}
     */
    public DomainDTO getById(Long id) {
        return domainMapper.toDomainDTO(domainRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<Domain>}
     */
    private JPAQuery<Domain> buildQuery(DomainDTO dto) {
        QDomain qdomain = QDomain.domain1;
        QServer qserver = QServer.server;
        JPAQuery<Domain> query = jpaQueryFactory
                .selectFrom(qdomain)
                .innerJoin(qdomain.server).fetchJoin();
        query.where(new Predicate[]{
                dto.getId() != null ? qdomain.id.eq(dto.getId()) : null,
        });
        query.orderBy(qdomain.id.desc());
        return query;
    }

    /**
     * 域名列表
     *
     * @param dto 查询条件
     * @return {@link List<DomainDTO>}
     */
    public List<DomainDTO> list(DomainDTO dto) {
        JPAQuery<Domain> query = buildQuery(dto);
        return domainMapper.toDomainDTOList(query.fetch());
    }

    /**
     * 分页查询域名
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<DomainDTO>}
     */
    public PageDTO<DomainDTO> page(DomainDTO dto, Integer current, Integer pageSize) {
        JPAQuery<Domain> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<Domain> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, domainMapper.toDomainDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存域名
     *
     * @param dto {@link DomainDTO}
     * @return {@link DomainDTO}
     */
    public DomainDTO save(DomainDTO dto) {
        Domain domain = domainMapper.toDomain(dto);
        if (dto.getId() != null) {
            domain = domainRepository.findById(dto.getId()).orElse(null);
        }
        domainMapper.partialUpdate(dto, domain);
        assert domain != null;
        return domainMapper.toDomainDTO(domainRepository.save(domain));
    }

    /**
     * 通过ID删除域名
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        domainRepository.deleteById(id);
        return true;
    }
}