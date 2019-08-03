package com.liuyao.demo.config.runner;

import com.liuyao.demo.util.LyLogUtil;
import com.liuyao.demo.ws.server.WbSvc;
import com.liuyao.demo.ws.server.impl.WbSvcImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import javax.xml.ws.Endpoint;

@Component
@Order(value = 1)
public class MyRunner1 implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        LyLogUtil.logInfo("这是自启动方法。");

//        Endpoint.publish("http://127.0.0.1:9090/wbsvc", new WbSvcImpl());

        //发布webserice 仅jdk7可用
//        WbSvc wbSvc = new WbSvcImpl();
//        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
//        factory.setServiceClass(WbSvc.class);
//        factory.setAddress("http://localhost:9090/wbsvc");
//        factory.setServiceBean(wbSvc);
//        factory.create();
//        System.out.println("Server start...");
    }
}
