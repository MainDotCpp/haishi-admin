package com.haishi.admin.shortlink.service;

import com.haishi.admin.cloak.dto.CloakCheckResult;
import com.haishi.admin.cloak.enums.CloakScene;
import com.haishi.admin.cloak.service.CloakService;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.common.exception.BizExceptionEnum;
import com.haishi.admin.shortlink.dao.ShortLinkConfigRepository;
import com.haishi.admin.shortlink.entity.ShortLinkConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkService {
    private final ShortLinkConfigRepository shortLinkConfigRepository;
    private final CloakService cloakService;

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

    @SneakyThrows
    public Object access(String key, HttpServletRequest request, Boolean preview) {
        ShortLinkConfig shortLinkConfig = getByKey(key);
        if (shortLinkConfig == null) throw new BizException(BizExceptionEnum.SHORT_LINK_NOT_EXIST);
        if (shortLinkConfig.getCloakId() == null) throw new BizException(BizExceptionEnum.CLOAK_LINK_NOT_CONFIG);

        RedirectView redirectView = new RedirectView(shortLinkConfig.getTargetUrl());
        redirectView.setStatusCode(HttpStatusCode.valueOf(301));
        if (Boolean.TRUE.equals(preview)) return redirectView;

        UUID cloakId = shortLinkConfig.getCloakId();
        CloakCheckResult result = cloakService.check(cloakId.toString(), request, cloakLog -> {
            cloakLog.setScene(CloakScene.SHORT_LINK);
            cloakLog.setRelatedId(shortLinkConfig.getId());
            return cloakLog;
        });
        if (result.getPermit()) return redirectView;
        redirectView.setStatusCode(HttpStatusCode.valueOf(404));
        return redirectView;
    }
}
