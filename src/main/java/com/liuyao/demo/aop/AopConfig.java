package com.wisely.highlight_spring4.chi.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//6：配置类
@Configuration
@ComponentScan("com.wisely.highlight_spring4.chi.aop")
@EnableAspectJAutoProxy //开启spring对aspectj的支持
public class AopConfig {


}
