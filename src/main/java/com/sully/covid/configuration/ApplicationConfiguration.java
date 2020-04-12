package com.sully.covid.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(csrfTokenAddingInterceptor());
        registry.addInterceptor(userRoleInterceptor());
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public HandlerInterceptor csrfTokenAddingInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) {
                CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (token != null && view != null) {
                    view.addObject(token.getParameterName(), token);
                }
            }
        };
    }

    @Bean
    HandlerInterceptor userRoleInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                if (modelAndView != null) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
                    List<String> roles = authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
                    modelAndView.addObject("roles", roles);

                    boolean isAdmin = authorities.stream().filter(a -> a.getAuthority().equals("ROLE_ADMIN")).count() > 0;
                    modelAndView.addObject("isAdmin", isAdmin);

                    boolean isLoggedIn = authorities.stream().filter(a -> a.getAuthority().equals("ROLE_ANONYMOUS")).count() == 0;
                    modelAndView.addObject("isLoggedIn", isLoggedIn);

                    modelAndView.addObject("username", authentication.getName());
                }
            }
        };
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.FRANCE);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
}
