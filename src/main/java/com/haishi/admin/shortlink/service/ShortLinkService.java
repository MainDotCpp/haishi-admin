package com.haishi.admin.shortlink.service;

import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.shortlink.dao.ShortLinkConfigRepository;
import com.haishi.admin.shortlink.entity.ShortLinkConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkService {
    private final ShortLinkConfigRepository shortLinkConfigRepository;

    public ShortLinkConfig getById(Long id) {
        return shortLinkConfigRepository.findById(id).orElse(null);
    }

    public ShortLinkConfig getByKey(String key) {
        return shortLinkConfigRepository.findByKey(key);
    }

    public PageDTO<ShortLinkConfig> getPage(PageDTO<ShortLinkConfig> pageDTO) {
        PageRequest pageRequest = PageRequest.of(pageDTO.getCurrent() - 1, pageDTO.getPageSize());
        Page<ShortLinkConfig> shortLinkPage = shortLinkConfigRepository.findAll(pageRequest);
        pageDTO.setData(shortLinkPage.getContent());
        pageDTO.setTotal(shortLinkPage.getTotalElements());
        return pageDTO;
    }

    public ShortLinkConfig save(ShortLinkConfig shortLinkConfig) {
        return shortLinkConfigRepository.save(shortLinkConfig);
    }

    public boolean delete(Long id) {
        shortLinkConfigRepository.deleteById(id);
        return true;
    }
}
