package com.haishi.admin.resource.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.resource.dao.ServerRepository;
import com.haishi.admin.resource.dto.ServerDTO;
import com.haishi.admin.resource.entity.Server;
import com.haishi.admin.resource.entity.QServer;
import com.haishi.admin.resource.mapper.ServerMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for {@link Server}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServerService {
    private final ServerRepository serverRepository;
    private final ServerMapper serverMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取服务器
     *
     * @param id ID
     * @return {@link ServerDTO}
     */
    public ServerDTO getById(Long id) {
        return serverMapper.toServerDTO(serverRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<Server>}
     */
    private JPAQuery<Server> buildQuery(ServerDTO dto) {
        QServer qserver = QServer.server;
        JPAQuery<Server> query = jpaQueryFactory.selectFrom(qserver);
        query.where(new Predicate[]{
                dto.id() != null ? qserver.id.eq(dto.id()) : null,
        });
        query.orderBy(qserver.id.desc());
        return query;
    }

    /**
     * 服务器列表
     *
     * @param dto 查询条件
     * @return {@link List<ServerDTO>}
     */
    public List<ServerDTO> list(ServerDTO dto) {
        JPAQuery<Server> query = buildQuery(dto);
        return serverMapper.toServerDTO(query.fetch());
    }

    /**
     * 分页查询服务器
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<ServerDTO>}
     */
    public PageDTO<ServerDTO> page(ServerDTO dto, Integer current, Integer pageSize) {
        JPAQuery<Server> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<Server> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, serverMapper.toServerDTO(results.getResults()), results.getTotal());
    }

    /**
     * 保存服务器
     *
     * @param dto {@link ServerDTO}
     * @return {@link ServerDTO}
     */
    public ServerDTO save(ServerDTO dto) {
        Server server = serverMapper.toServer(dto);
        if (dto.id() != null) {
            server = serverRepository.findById(dto.id()).orElse(null);
        }
        serverMapper.partialUpdate(dto, server);
        assert server != null;
        return serverMapper.toServerDTO(serverRepository.save(server));
    }

    /**
     * 通过ID删除服务器
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        serverRepository.deleteById(id);
        return true;
    }
}