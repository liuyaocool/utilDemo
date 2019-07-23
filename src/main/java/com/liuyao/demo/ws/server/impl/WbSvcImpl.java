package com.liuyao.demo.ws.server.impl;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class WbSvcImpl implements com.liuyao.demo.ws.server.WbSvc {

    @Override
    @WebMethod(exclude = true)
//    @WebResult(targetNamespace = "")
//    @RequestWrapper(localName = "sendBuesinessData", targetNamespace = "http://service.ws.business.ump.com/", className = "com.chat.worker.common.ump.SendBuesinessData")
//    @ResponseWrapper(localName = "sendBuesinessDataResponse", targetNamespace = "http://service.ws.business.ump.com/", className = "com.chat.worker.common.ump.SendBuesinessDataResponse")
    public String findAreaCount(String startDate, String endDate, String areaCode) {
        return "asdfghjkl";
    }

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder("http://127.0.0.1:9090/wbsvc");

        Endpoint.publish("http://127.0.0.1:9090/wbsvc", new WbSvcImpl());
    }
}
