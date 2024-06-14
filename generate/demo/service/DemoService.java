package com.haishi.admin.demo.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.demo.dao.DemoRepository;
import com.haishi.admin.demo.dto.DemoDTO;
import com.haishi.admin.demo.entity.QDemo;
import com.haishi.admin.demo.entity.Demo;
import com.haishi.admin.demo.mapper.DemoMapper;
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
 * Service for {@link Demo}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DemoService {
    private final DemoRepository demoRepository;
    private final DemoMapper demoMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取测试
     *
     * @param id ID
     * @return {@link DemoDTO}
     */
    public DemoDTO getById(Long id) {
        return demoMapper.toDemoDTO(demoRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<Demo>}
     */
    private JPAQuery<Demo> buildQuery(DemoDTO dto) {
        QDemo qdemo = QDemo.demo;
        JPAQuery<Demo> query = jpaQueryFactory
                .selectFrom(qdemo);
        query.where(new Predicate[]{
                dto.getId() != null ? qdemo.id.eq(dto.getId()) : null,
        });
        query.orderBy(qdemo.id.desc());
        return query;
    }

    /**
     * 测试列表
     *
     * @param dto 查询条件
     * @return {@link List<DemoDTO>}
     */
    public List<DemoDTO> list(DemoDTO dto) {
        JPAQuery<Demo> query = buildQuery(dto);
        return demoMapper.toDemoDTOList(query.fetch());
    }

    /**
     * 分页查询测试
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<DemoDTO>}
     */
    public PageDTO<DemoDTO> page(DemoDTO dto, Integer current, Integer pageSize) {
        JPAQuery<Demo> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<Demo> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, demoMapper.toDemoDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存测试
     *
     * @param dto {@link DemoDTO}
     * @return {@link DemoDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public DemoDTO save(DemoDTO dto) {
        Demo demo = new Demo();
        if (dto.getId() != null)
            demo = demoRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:测试不存在"));
        demoMapper.partialUpdate(dto, demo);
        return demoMapper.toDemoDTO(demoRepository.save(demo));
    }

    /**
     * 通过ID删除测试
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        demoRepository.deleteById(id);
        return true;
    }
}