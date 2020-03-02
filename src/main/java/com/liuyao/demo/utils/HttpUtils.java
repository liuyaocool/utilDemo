package com.liuyao.demo.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtils {

    public static String doAjaxt(String urlStr, String method, String encoding,
                                 Map<String, String> headers, Map<String, String> data){
        OutputStreamWriter paramout = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String result = null;

        try{
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod(method);
            for(String key: headers.keySet()){
                conn.setRequestProperty(key, headers.get(key));
            }
            conn.setDoOutput(true);

            paramout = new OutputStreamWriter(conn.getOutputStream(), encoding);
            if (null != data && data.size() > 0){
                JSONObject json = new JSONObject();
                for (String key : data.keySet()) {
                    json.put(key, data.get(key));
                }
                paramout.write(json.toString());
            }
            paramout.flush();

            isr = new InputStreamReader(conn.getInputStream(), encoding);
            br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            result = sb.toString();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(paramout);
            close(isr);
            close(br);
        }
        return result;
    }


    public static void close(AutoCloseable obj){
        try {
            obj.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
