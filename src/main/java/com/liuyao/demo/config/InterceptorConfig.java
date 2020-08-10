package com.liuyao.demo.config;

import com.liuyao.demo.interceptor.MacHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MacHandlerInterceptor())
//                .addPathPatterns("/**")
                /**
                 * 系统可访问内静态资源 见：addResourceHandlers
                 * 默认静态资源是映射到 “/**” 下的
                 * 静态文件不经过拦截器
                 * 配置此处 需搭配spring.mvc.static-path-pattern: /sta/**
                 * 页面引用 也须加前缀 sta/
                 */
                .excludePathPatterns(new String[]{"/sta/**"})
        ;
    }

    //重写此方法 可访问到静态文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                //配置静态资源路径 则不会出现静态资源no mapping现象
                .addResourceLocations("classpath:/static/")
        ;
    }
}
