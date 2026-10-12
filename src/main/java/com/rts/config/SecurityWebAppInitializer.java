package com.rts.config;

import javax.servlet.ServletContext;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/** springSecurityFilterChain 등록. 한글 깨짐 방지를 위해 인코딩 필터를 보안 필터보다 먼저 건다. */
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new CharacterEncodingFilter("UTF-8", true));
    }
}
