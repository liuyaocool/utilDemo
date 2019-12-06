package com.liuyao.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.UUID;

public class IOUtil {

    /**
     * 文件上传 根据实际修改
     * @param file
     * @return
     */
    public static String upload(MultipartFile file, String path){
        HttpServletRequest request = ServletUtil.getRequest();
        String upload = request.getSession().getServletContext().getRealPath("upload");
        String resourse = ServletUtil.getRequest().getServletContext().getRealPath("resources");
        String base = System.getProperty("catalina.home"); //获取tomcat根路径
        System.out.println("upload：" + upload);
        System.out.println("resourse：" + resourse);
        System.out.println("base：" + base);

        String url = "";
        if (!file.isEmpty()) {
            //随机生成一个唯一的名字
            String mz = file.getOriginalFilename();
            System.out.println(mz);//3.jpg
            String[] mmm = mz.split("\\.");
            String fileName = UUID.randomUUID().toString()+"."+mmm[1];

            File targetFile = new File(path, fileName);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            try {
                file.transferTo(targetFile);//保存文件
            } catch (Exception e) {
                e.printStackTrace();
            }

            url = "/upload/"+fileName;
        }
        return url;
    }

    public static boolean saveFile(MultipartFile file, String folderPath, String fileName){
        try {
            if (!file.isEmpty()) {
                newFolder(folderPath);
                file.transferTo(new File(folderPath, fileName));
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean newFile(String folderPath, String fileName, boolean cover){
        newFolder(folderPath);
        File file = new File(folderPath + "/" + fileName);
        if (cover || !file.exists()){
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void newFolder(String folder){
        File targetFile = new File(folder);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
    }

    public static String readFile(String path, String encoding){
        BufferedReader reader = null;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        encoding = null == encoding ? "UTF-8" : encoding;
        StringBuilder res = new StringBuilder();
        try{
            fis = new FileInputStream(path);
            isr = new InputStreamReader(fis, encoding);
            reader = new BufferedReader(isr);
            String line;
            while((line = reader.readLine()) != null){
                res.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            close(fis);
            close(isr);
            close(reader);
        }
        return res.toString();
    }

    public static String fileMd5(MultipartFile file){
        try {
            return DigestUtils.md5Hex(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(AutoCloseable c){
        try {
            if (null != c) c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
