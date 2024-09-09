package com.haishi.admin.resource.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.resource.dao.DomainAccountRepository;
import com.haishi.admin.resource.dto.DomainAccountDTO;
import com.haishi.admin.resource.dto.GoddyDomainDTO;
import com.haishi.admin.resource.entity.Domain;
import com.haishi.admin.resource.entity.QDomainAccount;
import com.haishi.admin.resource.entity.DomainAccount;
import com.haishi.admin.resource.mapper.DomainAccountMapper;
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
 * Service for {@link DomainAccount }
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DomainAccountService {
    private final DomainAccountRepository domainAccountRepository;
    private final DomainAccountMapper domainAccountMapper;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据ID获取域名账户
     *
     * @param id ID
     * @return {@link DomainAccountDTO}
     */
    public DomainAccountDTO getById(Long id) {
        return domainAccountMapper.toDomainAccountDTO(domainAccountRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<DomainAccount>}
     */
    private JPAQuery<DomainAccount> buildQuery(DomainAccountDTO dto) {
        QDomainAccount qdomainAccount = QDomainAccount.domainAccount;
        JPAQuery<DomainAccount> query = jpaQueryFactory
                .selectFrom(qdomainAccount);
        query.where(new Predicate[]{
                dto.getId() != null ? qdomainAccount.id.eq(dto.getId()) : null,
        });
        query.orderBy(qdomainAccount.id.desc());
        return query;
    }

    /**
     * 域名账户列表
     *
     * @param dto 查询条件
     * @return {@link List<DomainAccountDTO>}
     */
    public List<DomainAccountDTO> list(DomainAccountDTO dto) {
        JPAQuery<DomainAccount> query = buildQuery(dto);
        return domainAccountMapper.toDomainAccountDTOList(query.fetch());
    }

    /**
     * 分页查询域名账户
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<DomainAccountDTO>}
     */
    public PageDTO<DomainAccountDTO> page(DomainAccountDTO dto, Integer current, Integer pageSize) {
        JPAQuery<DomainAccount> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<DomainAccount> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, domainAccountMapper.toDomainAccountDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存域名账户
     *
     * @param dto {@link DomainAccountDTO}
     * @return {@link DomainAccountDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public DomainAccountDTO save(DomainAccountDTO dto) {
        DomainAccount domainAccount = new DomainAccount();
        if (dto.getId() != null)
            domainAccount = domainAccountRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:域名账户不存在"));
        domainAccountMapper.partialUpdate(dto, domainAccount);
        return domainAccountMapper.toDomainAccountDTO(domainAccountRepository.save(domainAccount));
    }

    /**
     * 通过ID删除域名账户
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        domainAccountRepository.deleteById(id);
        return true;
    }

    public List<GoddyDomainDTO> getDomainList(Long accountId){
        DomainAccount domainAccount = this.domainAccountRepository.findById(accountId).orElseThrow(() -> new BizException("系统错误:域名账户不存在"));
        return List.of(new GoddyClient(domainAccount).getDomainList());
    }

    public boolean configDns(Domain domain) {
        DomainAccount domainAccount = this.domainAccountRepository.findById(domain.getAccountId()).orElseThrow(() -> new BizException("系统错误:域名账户不存在"));
        new GoddyClient(domainAccount).configDns(domain.getDomain(),domain.getServer().getIp());
        return true;
    }
}