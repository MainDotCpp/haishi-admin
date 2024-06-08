package com.haishi.admin.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.haishi.admin.system.dto.DeptQueryDTO;
import com.haishi.admin.system.entity.QDept;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dao.DeptRepository;
import com.haishi.admin.system.entity.Dept;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeptService {
    private final DeptRepository deptRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Dept getById(Long id) {
        return deptRepository.findById(id).orElse(null);
    }

    private JPAQuery<Dept> buildQuery(DeptQueryDTO queryDTO) {
        JPAQuery<Dept> query = jpaQueryFactory.selectFrom(QDept.dept);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(QDept.dept.id.desc());
        return query;
    }

    public List<Dept> list(DeptQueryDTO queryDTO) {
        JPAQuery<Dept> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<Dept> page(DeptQueryDTO queryDTO) {
        JPAQuery<Dept> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<Dept> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public Dept save(Dept dept) {
        return deptRepository.save(dept);
    }

    public boolean delete(Long id) {
        deptRepository.deleteById(id);
        return true;
    }

    public Tree<String> getDeptTree() {
        Iterable<Dept> depts = deptRepository.findAll();
        ArrayList<TreeNode<String>> nodeList = CollUtil.newArrayList();

        for (Dept dept : depts) {
            nodeList.add(new TreeNode<>(dept.getId(), dept.getParentId(), dept.getName(), 0));
        }
         return TreeUtil.buildSingle(nodeList, "0");
    }
}