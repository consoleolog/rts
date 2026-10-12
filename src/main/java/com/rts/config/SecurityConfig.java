package com.rts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 기본 골격.
 *
 * <p>사용자 테이블과 UserDetailsService가 아직 없으므로 지금은 모든 요청을 허용한다.
 * 로그인 기능을 만들 때 authorizeHttpRequests 규칙과 formLogin을 추가한다.
 * 보안 설정은 루트 컨텍스트에 있어 MVC 매처(HandlerMappingIntrospector)를 쓸 수 없으니
 * URL 규칙은 AntPathRequestMatcher로 작성한다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
