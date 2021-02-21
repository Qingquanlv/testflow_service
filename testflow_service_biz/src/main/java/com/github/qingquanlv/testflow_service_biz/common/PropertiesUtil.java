package com.github.qingquanlv.testflow_service_biz.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/19 22:05
 * @Version 1.0
 */
public class PropertiesUtil {

    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Properties props;

    //Tomcat运行时执行
    //代码块执行顺序：静态代码块>普通代码块>构造代码块
    //构造代码块每次都执行，但是静态代码块只执行一次
    static {
        String fileName = "redis.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            //logger.error("配置文件读取异常",e);
        }
    }
    //自定义俩个get方法，方便调用工具类读取properties文件的属性
    public static String getProperty(String key){
        String value= props.getProperty(key.trim());
        if (null == value || "".equals(value)){
            return null;
        }
        return value.trim();
    }
    public static String getProperty(String key,String defaultValue){
        String value= props.getProperty(key.trim());
        if (null == value || "".equals(value)){
            value = defaultValue;
        }
        return value.trim();
    }
}

