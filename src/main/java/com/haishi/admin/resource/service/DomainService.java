package com.haishi.admin.resource.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import com.haishi.admin.common.ThreadUserinfo;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.common.utils.QueryUtils;
import com.haishi.admin.resource.dao.DomainRepository;
import com.haishi.admin.resource.dto.DomainDTO;
import com.haishi.admin.resource.entity.DomainAgentConfig;
import com.haishi.admin.resource.entity.QDomain;
import com.haishi.admin.resource.entity.Domain;
import com.haishi.admin.resource.mapper.DomainMapper;
import com.haishi.admin.system.entity.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
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
    private final RestTemplate restTemplate;

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

        var predicates = new ArrayList<Predicate>();

        if (dto.getOwnerId() != null && dto.getOwnerId() == 0L) {
            predicates.add(qdomain.owner.id.isNull());
        } else {
            QueryUtils.dataPermissionFilter(qdomain._super, predicates);
        }
        if (dto.getId() != null) {
            predicates.add(qdomain.id.eq(dto.getId()));
        }

        query.where(predicates.toArray(Predicate[]::new));
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
        if (dto.getId() != null)
            domain = domainRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:域名不存在"));
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

    public DomainAgentConfig getAgentConfig(Long id) {
        return domainMapper.toDomainAgentConfig(domainRepository.findById(id).orElseThrow());
    }


    public void deploy(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow();
        // deploy domain
        try {
            restTemplate.getForEntity("http://localhost:8000/deploy/domain?domain_id=" + domain.getId(), String.class);
        } catch (Exception e) {
            log.error("Deploy domain failed", e);
            throw new BizException("部署失败");
        }
    }

    public void receive(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow();
        Assert.isNull(domain.getOwner(), "域名已被领取");
        var owner = new User();
        owner.setId(ThreadUserinfo.get().getId());
        domain.setOwner(owner);
        domainRepository.save(domain);
    }
}