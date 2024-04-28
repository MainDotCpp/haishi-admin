package com.haishi.admin.cloak.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.cloak.dao.CloakLogRepository;
import com.haishi.admin.cloak.entity.CloakLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkService {
    private finalCloakLogRepository cloakLogRepository;

    publicCloakLog getById(Long id) {
        return cloakLogRepository.findById(id).orElse(null);
    }

    public PageDTO<CloakLog> getPage(PageDTO<CloakLog> pageDTO) {
        PageRequest pageRequest = PageRequest.of(pageDTO.getCurrent() - 1, pageDTO.getPageSize());
        Page<CloakLog> cloakLogPage = cloakLogRepository.findAll(pageRequest);
        pageDTO.setData(cloakLogPage.getContent());
        pageDTO.setTotal(cloakLogPage.getTotalElements());
        return pageDTO;
    }

    publicCloakLog save(CloakLog cloakLog) {
        return cloakLogRepository.save(cloakLog);
    }

    public boolean delete(Long id) {
        cloakLogRepository.deleteById(id);
        return true;
    }
}