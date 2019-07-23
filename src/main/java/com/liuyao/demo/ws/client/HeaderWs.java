package com.liuyao.demo.ws.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;

public class HeaderWs {

    /**
     * 单笔短信推送请求地址
     */
    private final static String singleUrl = "http://localhost:8080/untitled3/services/HelloWorld";

    /**
     * 批量短信推送请求地址
     */
    private final static String batUrl = "http://localhost:8080/sms/smsReceiveAction_batReceiveSms.action";

    public static void main(String[] args) {

        doPostSingle();
    }

    /**
     *单笔请求方法
     * @return
     */
    public static String doPostSingle(){
        String url = "http://10.206.1.81:8006/AppService.asmx/GeProjectPlan";
        CloseableHttpClient client = HttpClients.createDefault();
        String res = null;
        try {
            HttpPost httpPost = new HttpPost(url);
//            httpPost.setHeader("Content-type", "text/xml");
//            httpPost.setHeader("SOAPAction", "http://www.qigousoft.com/GeProjectPlan");
            CloseableHttpResponse response = null;
            try {
//                String wsdlData="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                        "  <soap:Body>\n" +
//                        "    <GeProjectPlan xmlns=\"http://www.qigousoft.com/\" />\n" +
//                        "  </soap:Body>\n" +
//                        "</soap:Envelope>";
//                StringEntity s = new StringEntity("");
//                s.setContentEncoding("UTF-8");
//                s.setContentType("text/xml");//发送json数据需要设置contentType
//                httpPost.setEntity(s);
                response = client.execute(httpPost);
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                if(entity!=null){
                    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                        String result = EntityUtils.toString(response.getEntity());// 返回json格式：
                        System.out.println(result);

                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        StringReader sr = new StringReader(result);
                        InputSource is = new InputSource(sr);
                        Document document = db.parse(is);
                        Element root = document.getDocumentElement();
                        NodeList nodelist_return = root.getElementsByTagName("fromReturn");
                        String returnCont = nodelist_return.item(0).getTextContent();
                        //打印返回信息
                        System.out.println("webservice返回信息："+returnCont);

//                        res = JSONObject.fromObject(returnCont);
//                        System.out.println(res.toString());
                    }
                }
                EntityUtils.consume(entity);

            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;

    }


}
