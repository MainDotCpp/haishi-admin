package com.haishi.admin.resource.service;

import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.enums.CloakScene;
import com.haishi.admin.cloak.service.CloakService;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.resource.dao.WebsiteRepository;
import com.haishi.admin.resource.dto.WebsiteCloakCheckDTO;
import com.haishi.admin.resource.dto.WebsiteDTO;
import com.haishi.admin.resource.entity.QWebsite;
import com.haishi.admin.resource.entity.Website;
import com.haishi.admin.resource.mapper.WebsiteMapper;
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
 * Service for {@link Website}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebsiteService {
    private final WebsiteRepository websiteRepository;
    private final WebsiteMapper websiteMapper;
    private final JPAQueryFactory jpaQueryFactory;
    private final CloakService cloakService;

    /**
     * 根据ID获取网站
     *
     * @param id ID
     * @return {@link WebsiteDTO}
     */
    public WebsiteDTO getById(Long id) {
        return websiteMapper.toWebsiteDTO(websiteRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<Website>}
     */
    private JPAQuery<Website> buildQuery(WebsiteDTO dto) {
        QWebsite qwebsite = QWebsite.website;
        JPAQuery<Website> query = jpaQueryFactory
                .selectFrom(qwebsite);
        query.where(
                dto.getId() != null ? qwebsite.id.eq(dto.getId()) : null,
                dto.getDomainId() != null ? qwebsite.domain.id.eq(dto.getDomainId()) : null
        );
        query.orderBy(qwebsite.id.desc());
        return query;
    }

    /**
     * 网站列表
     *
     * @param dto 查询条件
     * @return {@link List<WebsiteDTO>}
     */
    public List<WebsiteDTO> list(WebsiteDTO dto) {
        JPAQuery<Website> query = buildQuery(dto);
        return websiteMapper.toWebsiteDTOList(query.fetch());
    }

    /**
     * 分页查询网站
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<WebsiteDTO>}
     */
    public PageDTO<WebsiteDTO> page(WebsiteDTO dto, Integer current, Integer pageSize) {
        JPAQuery<Website> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<Website> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, websiteMapper.toWebsiteDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存网站
     *
     * @param dto {@link WebsiteDTO}
     * @return {@link WebsiteDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public WebsiteDTO save(WebsiteDTO dto) {
        Website website = new Website();
        if (dto.getId() != null)
            website = websiteRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:网站不存在"));
        websiteMapper.partialUpdate(dto, website);
        return websiteMapper.toWebsiteDTO(websiteRepository.save(website));
    }

    /**
     * 通过ID删除网站
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        websiteRepository.deleteById(id);
        return true;
    }

    public CloakCheckResult check(WebsiteCloakCheckDTO dto) {
        return cloakService.check(dto.cloakKey(), dto.clientInfo(), cloakLog -> {
            cloakLog.setScene(CloakScene.WEBSITE);
            cloakLog.setRelatedId(dto.websiteId());
            return cloakLog;
        });
    }
}