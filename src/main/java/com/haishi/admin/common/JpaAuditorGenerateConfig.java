package com.haishi.admin.common;

import com.haishi.admin.system.dto.Userinfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class JpaAuditorGenerateConfig implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Optional<Userinfo> userinfo = Optional.ofNullable(ThreadUserinfo.get());
        return userinfo.map(Userinfo::getId).or(Optional::empty);
    }
}
