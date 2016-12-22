package com.alpha.tools.shiro.javaconfig;

import com.alpha.tools.shiro.filter.CacheFilter;
import com.alpha.tools.shiro.redis.RedisSessionDAO;
import com.alpha.tools.shiro.session.SessionManager;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by cw on 15-11-19.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Slf4j
@Configuration
public class ShiroConfiguration {
    private final static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

    @Bean(name = "shiroFilter")
    @DependsOn({"securityManager","cacheFilter"})
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager,CacheFilter cacheFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        filterChainDefinitionMap.put("/health", "anon");
//        filterChainDefinitionMap.put("/init/**", "anon");
        shiroFilterFactoryBean.setFilters(new HashMap<String, Filter>(){{
            put("cacheFilter",cacheFilter);
        }});
        filterChainDefinitionMap.put("/**", "anon,cacheFilter");
//        shiroFilterFactoryBean.getFilters().put("loginAuthc",authcFilter());
        shiroFilterFactoryBean.setLoginUrl("/init/unauthorized");
        shiroFilterFactoryBean.setUnauthorizedUrl("/init/unauthorized");



        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    @DependsOn({"securityRealm","sessionManager"})
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm,SessionManager sessionManager) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(realm);
        dwsm.setSessionManager(sessionManager);
        return dwsm;
    }

    @Bean(name = "sessionManager")
    @DependsOn("redisSessionDAO")
    public SessionManager getDefaultWebSessionManager(RedisSessionDAO redisSessionDAO) {
        SessionManager sessionManager = new SessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    @DependsOn("securityManager")
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }
}
