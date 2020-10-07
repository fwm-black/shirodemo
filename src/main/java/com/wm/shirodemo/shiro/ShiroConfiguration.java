package com.wm.shirodemo.shiro;

import com.wm.shirodemo.shiro.cache.RedisCacheManager;
import com.wm.shirodemo.shiro.filter.ShiroLoginFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfiguration {

    /**
     * securityManager
     * @param realm
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("Realm")Realm realm,@Qualifier("credentialsMatcher")CredentialsMatcher credentialsMatcher){

//        设置自定义凭证匹配器
        realm.setCredentialsMatcher(credentialsMatcher);

//        开启缓存管理（shiro默认缓存管理）----------------------------------------------------------------------------
        realm.setCachingEnabled(true);
        realm.setAuthenticationCachingEnabled(true);        //认证缓存
        realm.setAuthenticationCacheName("authenticationCache");        //缓存名称（认证）
        realm.setAuthorizationCachingEnabled(true);     //授权缓存
        realm.setAuthorizationCacheName("authorizationCache");      //缓存名称（授权）
//        realm.setCacheManager(new EhCacheManager());      //默认ehcache缓存管理器
        realm.setCacheManager(new RedisCacheManager());     //自定义redis缓存管理器
//        //////////////-----------------------------------------------------------------------------------------------------

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        return securityManager;

    }

    /**
     * shiroFilter
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //      配置系统资源
        Map<String,String> map = new HashMap<String,String>();

        //      所有资源都需要认证和授权
        map.put("/login","anon");

        map.put("/register","anon");

        map.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

//-------自定义filter-----------------------------------------------------------
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        //      指定认证过滤器
        filters.put("authc",new ShiroLoginFilter());
        //      注入自定义filter
        shiroFilterFactoryBean.setFilters(filters);
//------////////////------------------------------------------------------------

        return shiroFilterFactoryBean;

    }

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher () {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //  密码加密方式
        credentialsMatcher.setHashAlgorithmName("md5");
        //  指定散列次数
        credentialsMatcher.setHashIterations(22);
        return credentialsMatcher;
    }

    /**
     * 开启shiro注解
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
