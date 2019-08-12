package com.liuyao.demo.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringUtils {

    public static <T> T findCrossConfig(Class<T> claz){
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        return context.getBean(claz);
    }

}
