package com.yuan.yiyao.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yuan.yiyao.ex.MyException;
import com.yuan.yiyao.ex.MyExceptionHandller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 自定义组件
 */
@Configuration
public class MyComponents {

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 接送转换
     */
//    @Bean
//    public HttpMessageConverter<Object> httpMessageConverter(){
//        return new MappingJackson2HttpMessageConverter();
//    }

    @Bean
    public MyExceptionHandller myExceptionHandller(){
        MyExceptionHandller myExceptionHandller = new MyExceptionHandller();
//        myExceptionHandller.setJsonConverter(httpMessageConverter);
        return myExceptionHandller;
    }


}
