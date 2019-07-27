package com.liuyao.demo.ws.client;

import com.liuyao.demo.ws.User;
import com.liuyao.demo.ws.test.Ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class SoapUtil {

    /*
     * 远程访问SOAP协议接口
     *
     * @param url： 服务接口地址"http://192.168.0.120:8222/HelloWorld?wsdl"
     * @param isClass：接口类名
     * @param isMethod： 接口方法名
     * @param sendSoapString： soap协议xml格式访问接口
     *
     * @return  soap协议xml格式
     *
     * @备注：有四种请求头格式1、SOAP 1.1； 2、SOAP 1.2 ； 3、HTTP GET； 4、HTTP POST
     * 参考---》http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?op=getWeatherbyCityName
     */
    public static String getWebServiceAndSoap(String url,String isClass,String isMethod,String soap) throws IOException {
        if (soap == null) {
            return null;
        }
        URL soapUrl = new URL(url);
        URLConnection conn = soapUrl.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Host","sdsapi.nrcc.com.cn");
        conn.setRequestProperty("Content-Length",
                Integer.toString(soap.length()));
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        // 调用的接口方法是
        conn.setRequestProperty("SOAPAction", "http://tempuri.org/" + isMethod);
        OutputStream os = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
        osw.write(soap);
        osw.flush();
        osw.close();
        // 获取webserivce返回的流
        InputStream is = conn.getInputStream();
        if (is!=null) {
            byte[] bytes = new byte[0];
            bytes = new byte[is.available()];
            is.read(bytes);
            String str = new String(bytes);
            return str;
        }else {
            return null;
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        StringBuffer sendSoapString = new StringBuffer();
        String pwd = Ws.doPostSingle("http://172.23.121.147:8110/Handler1.ashx",
                null, null, null, null);
        String aa =
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Header>\n" +
                "    <AuthorSoapHeader xmlns=\"http://tempuri.org/\">\n" +
                "      <Secretkey>"+pwd+"</Secretkey>\n" +
                "    </AuthorSoapHeader>\n" +
                "  </soap:Header>\n" +
                "  <soap:Body>\n" +
                "    <GetIdentityList xmlns=\"http://tempuri.org/\">\n" +
                "      <searchModel>\n" +
                "        <IDEN_DATA_ID></IDEN_DATA_ID>\n" +
                "        <BASE_BRANCH_MANAGE_ID></BASE_BRANCH_MANAGE_ID>\n" +
                "        <BASE_VERSION_MANAGE_ID></BASE_VERSION_MANAGE_ID>\n" +
                "        <CHEM_NAME></CHEM_NAME>\n" +
                "        <CHEM_NAME_VAGUE></CHEM_NAME_VAGUE>\n" +
                "        <CHEM_CAS>62-53-3</CHEM_CAS>\n" +
                "        <CHEM_SHAPE></CHEM_SHAPE>\n" +
                "        <CHEM_CATEGORY></CHEM_CATEGORY>\n" +
                "        <CHEM_ALIAS></CHEM_ALIAS>\n" +
                "        <IDEN_ANNEX_ID></IDEN_ANNEX_ID>\n" +
                "      </searchModel>\n" +
                "    </GetIdentityList>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
        sendSoapString.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tes=\"http://test03/\">");
        sendSoapString.append("   <soapenv:Header/>");
        sendSoapString.append("   <soapenv:Body>");
        sendSoapString.append("      <tes:getSum>");
        sendSoapString.append("         <arg0>66</arg0>");
        sendSoapString.append("         <arg1>33</arg1>");
        sendSoapString.append("      </tes:getSum>");
        sendSoapString.append("   </soapenv:Body>");
        sendSoapString.append("</soapenv:Envelope>");

        String url = "http://sdsapi.nrcc.com.cn/DataExtractService.asmx";
        String namespace = "http://tempuri.org/";
        int timeout = 0;
        String method = "GetIdentityList";

        try {
            String ret= getWebServiceAndSoap(url,"",method, aa);
            System.out.println(ret);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
