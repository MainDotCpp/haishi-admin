package com.haishi.admin.resource.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.compress.CompressUtil;
import com.haishi.admin.common.HaishiConfig;
import com.haishi.admin.common.dto.PageDTO;
import com.haishi.admin.common.exception.BizException;
import com.haishi.admin.resource.dao.LandingRepository;
import com.haishi.admin.resource.dto.LandingDTO;
import com.haishi.admin.resource.entity.QLanding;
import com.haishi.admin.resource.entity.Landing;
import com.haishi.admin.resource.mapper.LandingMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Service for {@link Landing}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LandingService {
    private final LandingRepository landingRepository;
    private final LandingMapper landingMapper;
    private final JPAQueryFactory jpaQueryFactory;
    private final HaishiConfig haishiConfig;

    /**
     * 根据ID获取落地页
     *
     * @param id ID
     * @return {@link LandingDTO}
     */
    public LandingDTO getById(Long id) {
        return landingMapper.toLandingDTO(landingRepository.findById(id).orElse(null));
    }

    /**
     * 构建查询
     *
     * @param dto 查询条件
     * @return {@link JPAQuery<Landing>}
     */
    private JPAQuery<Landing> buildQuery(LandingDTO dto) {
        QLanding qlanding = QLanding.landing;
        JPAQuery<Landing> query = jpaQueryFactory
                .selectFrom(qlanding);
        query.where(new Predicate[]{
                dto.getId() != null ? qlanding.id.eq(dto.getId()) : null,
        });
        query.orderBy(qlanding.id.desc());
        return query;
    }

    /**
     * 落地页列表
     *
     * @param dto 查询条件
     * @return {@link List<LandingDTO>}
     */
    public List<LandingDTO> list(LandingDTO dto) {
        JPAQuery<Landing> query = buildQuery(dto);
        return landingMapper.toLandingDTOList(query.fetch());
    }

    /**
     * 分页查询落地页
     *
     * @param dto      查询条件
     * @param current  当前页
     * @param pageSize 每页大小
     * @return {@link PageDTO<LandingDTO>}
     */
    public PageDTO<LandingDTO> page(LandingDTO dto, Integer current, Integer pageSize) {
        JPAQuery<Landing> query = buildQuery(dto);
        query.offset((long) (current - 1) * pageSize).limit(pageSize);
        QueryResults<Landing> results = query.fetchResults();
        return new PageDTO<>(current, pageSize, landingMapper.toLandingDTOList(results.getResults()), results.getTotal());
    }

    /**
     * 保存落地页
     *
     * @param dto {@link LandingDTO}
     * @return {@link LandingDTO}
     */
    @Transactional(rollbackFor = Exception.class)
    public LandingDTO save(LandingDTO dto) {
        Landing landing = new Landing();
        if (dto.getId() != null)
            landing = landingRepository.findById(dto.getId()).orElseThrow(() -> new BizException("系统错误:落地页不存在"));
        landingMapper.partialUpdate(dto, landing);
        return landingMapper.toLandingDTO(landingRepository.save(landing));
    }

    /**
     * 通过ID删除落地页
     *
     * @param id ID
     * @return boolean
     */
    public boolean delete(Long id) {
        landingRepository.deleteById(id);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean downloadWeb(String url) {
        String path = IdUtil.fastUUID();
        Landing landing = new Landing();
        String webLibPath = haishiConfig.getWebLibPath();
        File webDir = FileUtil.file(webLibPath + "/" + path);
        log.info("下载落地页: web-path {}", webDir);

        String command = StrUtil.format(
                "/usr/bin/wget -user-agent=\"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4482.400 QQBrowser/9.7.13001.400\" -r -q -L -nd -k -p  -P {} {}",
                webDir.toString(),
                url
        );
        log.info("command: {}", command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            // 处理index文件
            log.info("处理index文件");
            // 生成压缩包
            if (!webDir.exists()) {
                throw new BizException("下载失败");
            }

            log.info("生成压缩包: {}", webDir + ".zip");
            CompressUtil.createArchiver(CharsetUtil.CHARSET_UTF_8, "zip", FileUtil.file(webDir + ".zip"))
                    .add(webDir)
                    .finish()
                    .close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new BizException("下载失败");
        }

        landing.setName(url);
        landing.setUuid(UUID.fromString(path));
        landingRepository.save(landing);
        return true;
    }

    public String reformIndex(String indexContent) {
        return "";
    }
}

