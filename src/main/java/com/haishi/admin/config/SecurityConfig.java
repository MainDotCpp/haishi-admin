package com.haishi.admin.config;

import com.haishi.admin.auth.service.AuthenticationSessionFilter;
import com.haishi.admin.system.service.UserService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;


    @Autowired
    private UserService userService;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加密方式
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationSessionFilter authenticationSessionFilter() {
        return new AuthenticationSessionFilter(userService, redissonClient);
    }

    /**
     * 认证管理器，登录的时候参数会传给 authenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("auth/login")
                .requestMatchers("v3/api-docs")
                .requestMatchers("l/");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth
                        .requestMatchers("POST", "auth/login").permitAll()
                        .requestMatchers("GET", "l/").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager(authenticationConfiguration))
                .addFilterBefore(authenticationSessionFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> {
                    form.successHandler((request, response, authentication) -> {
                        response.setContentType("application/json;charset=utf-8");
                        response.getWriter().write("登录成功");
                    });
                });
        return http.build();
    }


    /**
     * 跨域资源配置
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        //此处发现如果不加入自己的项目地址，会被拦截。
        configuration.setAllowedOriginPatterns(List.of("http://localhost:8083"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
