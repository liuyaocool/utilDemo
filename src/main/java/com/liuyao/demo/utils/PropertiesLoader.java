package com.liuyao.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置文件加载类
 * 优先级：jar包相对路径 > jar包相对路径/config > jar包相对路径/conf > jar包内部
 */
public class PropertiesLoader {

    protected static final Map<String, Properties> PROPS = new ConcurrentHashMap<>();
    private static final String JAR_PATH;
    private static final String[] RELATIVE_PATH = {"/", "/config/", "/conf/"};

    static {

        String startPath = new File("").getAbsolutePath().replace("\\", "/");
        String jarPath = PropertiesLoader.class.getClassLoader().getResource("").getPath()
                .replace("\\", "/").split(".jar")[0];
        JAR_PATH = jarPath.substring(jarPath.indexOf(startPath), jarPath.lastIndexOf("/"));
        System.out.println(JAR_PATH);

        // 有BUG，不会获得jar包所在绝对路径 而是start.bat所在绝对路径
        // 打印 D:\PROJECT\javatest
        System.out.println(new File("").getAbsolutePath());

        // 打印 file:/D:/PROJECT/javatest/target/demo-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/
        System.out.println(PropertiesLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println(PropertiesLoader.class.getProtectionDomain().getCodeSource().getLocation());
    }

    public static Properties getProperties(String propName){
        if (null == PROPS.get(propName)){
            synchronized (PropertiesLoader.class){
                if (null == PROPS.get(propName)){
                    Properties p = new Properties();
                    File file = new File("");
                    for (int i = 0; i < RELATIVE_PATH.length; i++) {
                        if ((file = new File(JAR_PATH + RELATIVE_PATH[i] + propName)).exists()) break;
                    }
                    InputStream is = null;
                    try {
                        if (file.exists()){
                            System.out.println("file exist");
                            System.out.println(file.getAbsolutePath());
                            is = new FileInputStream(file);
                        } else {
                            is = PropertiesLoader.class.getResourceAsStream("/" + propName);
                            if (is == null) {
                                is = PropertiesLoader.class.getClassLoader().getParent().getResourceAsStream("/" + propName);
                            }
                        }
                        p.load(is);
                        PROPS.put(propName, p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != is) is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return PROPS.get(propName);
    }


}
