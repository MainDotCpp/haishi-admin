package com.haishi.admin.shortlink.service;

import com.haishi.admin.shortlink.dto.ShortLinkGroupQueryDTO;
import com.haishi.admin.shortlink.entity.QShortLinkGroup;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.shortlink.dao.ShortLinkGroupRepository;
import com.haishi.admin.shortlink.entity.ShortLinkGroup;
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
public class ShortLinkGroupService {
    private final ShortLinkGroupRepository shortLinkGroupRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public ShortLinkGroup getById(Long id) {
        return shortLinkGroupRepository.findById(id).orElse(null);
    }

    private JPAQuery<ShortLinkGroup> buildQuery(ShortLinkGroupQueryDTO queryDTO) {
        JPAQuery<ShortLinkGroup> query = jpaQueryFactory.selectFrom(QShortLinkGroup.shortLinkGroup);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    public List<ShortLinkGroup> list(ShortLinkGroupQueryDTO queryDTO) {
        JPAQuery<ShortLinkGroup> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<ShortLinkGroup> page(ShortLinkGroupQueryDTO queryDTO) {
        JPAQuery<ShortLinkGroup> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<ShortLinkGroup> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public ShortLinkGroup save(ShortLinkGroup shortLinkGroup) {
        return shortLinkGroupRepository.save(shortLinkGroup);
    }

    public boolean delete(Long id) {
        shortLinkGroupRepository.deleteById(id);
        return true;
    }
}