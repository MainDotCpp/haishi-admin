package com.haishi.admin.system.service;

import com.haishi.admin.system.dto.UserQueryDTO;
import com.haishi.admin.system.entity.QUser;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.system.dao.UserRepository;
import com.haishi.admin.system.entity.User;
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
public class UserService {
    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    private JPAQuery<User> buildQuery(UserQueryDTO queryDTO) {
        JPAQuery<User> query = jpaQueryFactory.selectFrom(QUser.user);
        ArrayList<Predicate> predicates = new ArrayList<>();
        query.where(predicates.toArray(Predicate[]::new));
        return query;
    }

    public List<User> list(UserQueryDTO queryDTO) {
        JPAQuery<User> query = buildQuery(queryDTO);
        return query.fetch();
    }

    public PageDTO<User> page(UserQueryDTO queryDTO) {
        JPAQuery<User> query = buildQuery(queryDTO);
        queryDTO.setTotal(query.fetchCount());
        List<User> data = query.offset((long) (queryDTO.getCurrent() - 1) * queryDTO.getPageSize()).limit(queryDTO.getPageSize()).fetch();
        queryDTO.setData(data);
        return queryDTO;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}