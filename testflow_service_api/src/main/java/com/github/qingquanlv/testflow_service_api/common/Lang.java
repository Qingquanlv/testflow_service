package com.github.qingquanlv.testflow_service_api.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author: qingquan.lv
 */
public class Lang {

    public static String getErrorCode(Integer key) {
        String errorMsg = "";
        try {
            //创建properties对象
            Properties properties = new Properties();

            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("code.properties");
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            properties.load(bf);
            errorMsg = properties.getProperty(String.valueOf(key));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        //设置数据
        return errorMsg;
    }
}
