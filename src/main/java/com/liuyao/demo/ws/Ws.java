package com.liuyao.demo.ws;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ws {

    /**
     *单笔请求方法
     * @return
     */
    public String doPostSingle(String url, Map<String, String> header,
                                      String contentType, String encoding, String wsdl){
        CloseableHttpClient client = HttpClients.createDefault();
        String result = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity s;
            if (null == wsdl){
                s = new StringEntity("");
            } else {
                s = new StringEntity(wsdl);
            }
            if (null != header){
                for (String key : header.keySet()) {
                    httpPost.setHeader(key, header.get(key));
                }
            }
            if (null != contentType){
                httpPost.setHeader("Content-type", contentType);
                s.setContentType(contentType);
            }
            if (null != encoding){
                s.setContentEncoding(encoding);
            }
            httpPost.setEntity(s);
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if(entity!=null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(entity);
                System.out.println(result);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { response.close(); } catch (IOException e) { e.printStackTrace(); }
            try { client.close(); } catch (IOException e) { e.printStackTrace(); }
        }
        return result;

    }

    /**
     * 调用接口并返回数据
     * @param methodName
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> listApiData(String methodName) throws Exception {
        String url = "http://10.206.1.81:8006/AppService.asmx" + "/" + methodName;
        String xmlStr =  doPostSingle(url, null, null, null, null);
        Document document = getDocumentByStr(xmlStr);
        return analysisXmlFile(document);
    }

    /**
     * 解析xml
     */
    public List<Map<String, String>> analysisXmlFile(Document document) throws Exception {

        Element root = document.getRootElement();
        List<Element> keys = root.getChildren();
        for (int i = 0; i < 7; i++) {
            keys = keys.get(0).getChildren();
        }
        List<Element> dataEles = root.getChildren().get(1).getChildren().get(0).getChildren();
        List<Element> data;
        List<Map<String, String>> datas = new ArrayList<>();
        for (int i = 0; i < dataEles.size(); i++) {
            data = dataEles.get(i).getChildren();
            Map<String, String> dd = new HashMap<>();
            for (int j = 0; j < data.size(); j++) {
                dd.put(data.get(j).getName(), data.get(j).getValue());
            }
            datas.add(dd);
        }
        return datas;
    }

    public Document getDocumentByXml(String filePath) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        return builder.build(new File(filePath));
    }

    public Document getDocumentByStr(String xml) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xml));
        return doc;
    }

    public static void main(String[] args) {
        String[] methodNames = {"GetProject","GeProjectPlan","GetEmployee","GetEnterprise"};
        try {
            new Ws().listApiData(methodNames[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
