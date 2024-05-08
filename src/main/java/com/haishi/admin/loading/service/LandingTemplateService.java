package com.haishi.admin.loading.service;

import com.haishi.admin.loading.dto.LandingTemplateQueryDTO;
import com.haishi.admin.loading.entity.QLandingTemplate;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.loading.dao.LandingTemplateRepository;
import com.haishi.admin.loading.entity.LandingTemplate;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LandingTemplateService {
    private final LandingTemplateRepository landingTemplateRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public LandingTemplate getById(Long id) {
        return landingTemplateRepository.findById(id).orElse(null);
    }

    private JPAQuery<LandingTemplate> buildQuery(LandingTemplateQueryDTO queryDTO) {
        JPAQuery<LandingTemplate> query = jpaQueryFactory.selectFrom(QLandingTemplate.landingTemplate);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    public List<LandingTemplate> list(LandingTemplateQueryDTO queryDTO) {
        JPAQuery<LandingTemplate> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<LandingTemplate> page(LandingTemplateQueryDTO queryDTO) {
        JPAQuery<LandingTemplate> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<LandingTemplate> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public LandingTemplate save(LandingTemplate landingTemplate) {
        return landingTemplateRepository.save(landingTemplate);
    }

    public boolean delete(Long id) {
        landingTemplateRepository.deleteById(id);
        return true;
    }
}