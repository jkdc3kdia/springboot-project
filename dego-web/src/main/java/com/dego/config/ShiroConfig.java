package com.dego.config;

import cn.hutool.core.codec.Base64;
import com.dego.shiro.CustomerRealm;
import com.dego.shiro.RedisSessionDao;
import com.dego.shiro.ShiroUserFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    /**
     * 安全管理器
     *
     * @param customerRealm
     * @param sessionManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(CustomerRealm customerRealm, SessionManager sessionManager, CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customerRealm);
        securityManager.setSessionManager(sessionManager);
        // cookie manager
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }


    /**
     * 会话管理器
     *
     * @param sessionDAO
     * @return
     */
    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 60 * 2L);
        return sessionManager;
    }


    @Bean
    public SessionDAO sessionDAO(RedisTemplate redisTemplate) {
        RedisSessionDao sessionDao = new RedisSessionDao();
        sessionDao.setRedisTemplate(redisTemplate);
        return sessionDao;
    }


    /**
     * shiro过滤器
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登陆页
//        shiroFilterFactoryBean.setLoginUrl("/login.html");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 没有授权跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");
        // 自定义认证过滤器
        Map<String, Filter> filters = new HashMap<>(16);
        // user检验登陆状态
        filters.put("user", new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(filters);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/**/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/**/employee/login", "anon");
        filterChainDefinitionMap.put("/**/employee/logout", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 凭证匹配器
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // MD5加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 加密次数
        hashedCredentialsMatcher.setHashIterations(2);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }


    /**
     * 认证与授权
     *
     * @param hashedCredentialsMatcher
     * @return
     */
    @Bean
    public CustomerRealm customerRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        CustomerRealm customerRealm = new CustomerRealm();
        // 加密凭证
        customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return customerRealm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);//7天
        return simpleCookie;
    }
}
