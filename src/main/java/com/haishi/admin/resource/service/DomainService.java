package com.haishi.admin.resource.service;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.resource.dao.DomainRepository;
import com.haishi.admin.resource.dto.DomainDTO;
import com.haishi.admin.resource.entity.Domain;
import com.haishi.admin.resource.entity.QDomain;
import com.haishi.admin.resource.entity.QServer;
import com.haishi.admin.resource.mapper.DomainMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import com.querydsl.jpa.hibernate.HibernateUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final EntityManager entityManager;

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
        JPAQuery<Domain> query = jpaQueryFactory
                .selectFrom(qdomain);
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
    @Transactional(rollbackFor = Exception.class)
    public DomainDTO save(DomainDTO dto) {
        Domain domain = new Domain();
        if (dto.getId() != null) {
            Domain exist = jpaQueryFactory.selectFrom(QDomain.domain1).where(QDomain.domain1.id.eq(dto.getId())).fetchOne();
            domain = domainMapper.copy(exist);
        }
        domainMapper.partialUpdate(dto, domain);
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