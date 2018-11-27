package com.yuan.yiyao.config;

import com.yuan.yiyao.filter.MyShiroFilter;
import com.yuan.yiyao.operation.repository.OperationRepository;
import com.yuan.yiyao.operation.vo.Operation;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro的配置类
 */
@Configuration
public class MyShiroConfig {

    @Autowired
    private OperationRepository operationRepository;

    /**
     * 创建SiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("webSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        System.out.println("defaultWebSecurityManager---->"+defaultWebSecurityManager);
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        factoryBean.setSecurityManager(defaultWebSecurityManager);
        //设置当用户没有访问权限时跳转到登录页面
        factoryBean.setLoginUrl("/");
        //设置当权限不足时跳转的页面
        factoryBean.setUnauthorizedUrl("/unauthorized");
        //自定义权限拦截器
//        Map<String ,Filter> filters = new HashMap<>();
//        filters.put("myfilter",new MyShiroFilter());
        //添加拦截器
//        factoryBean.setFilters(filters);
        /*定义具体的url使用的拦截规则
            anon : 表示无须认证也可以访问
            authc : 表示需要认证才可以访问
            perms : 表示需要授权才可访问
         */
        Map<String,String > filterMap = new LinkedHashMap<>();
        filterMap.put("/","anon");
        filterMap.put("/login","anon");
        filterMap.put("/main","authc");
        //使用自定义的拦截器
        //得到系统中所有的权限访问url
        List<Operation> operations = operationRepository.findOperations();
        System.out.println("url----------------->"+operations.size());

        //对请求进行请求的控制
      if (operations!=null){
            for (Operation operation : operations){
                if (operation.getCode() !=null){
                    filterMap.put(operation.getUrl().trim(),"perms["+operation.getCode().trim()+"]");
                }
            }
        }

        //设置对访问url使用的拦截器
        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    /**
     * 配置DefaultWebSecurityManager
     */
    @Bean(name="webSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") Realm realm
                                ,@Qualifier("cacheManager")CacheManager cacheManager){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setCacheManager(cacheManager);
        //设置Realm
        manager.setRealm(realm);
        return manager;
    }

    /**
     * 定义缓存
     */
    @Bean(name = "cacheManager")
    public CacheManager cacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }
    /**
     * 配置Realm
     */
    @Bean(name="userRealm")
    public Realm getMyUserRealm(){
        return new MyUserRealm();
    }
}
