package com.LWM2Mserver.model.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by ss on 2017/4/28.
 */

public class PropertiesUtils {

    private static Properties _prop = new Properties();
    static {
        PropertiesUtils.readProperties("edgeServer.properties");
    }
    /**
     * 读取配置文件
     * @param fileName
     */
    public static void readProperties(String fileName){
        try {
            InputStream in = PropertiesUtils.class.getResourceAsStream("/"+fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            _prop.load(bf);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 根据key读取对应的value
     * @param key
     * @return
     */
    public static String getProperty(String key){
        return _prop.getProperty(key);
    }
}
