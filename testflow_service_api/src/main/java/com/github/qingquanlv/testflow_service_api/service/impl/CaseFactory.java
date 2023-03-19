package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.service.AbstractCaseFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/6 17:46
 */
@Component
public class CaseFactory extends AbstractCaseFactory {


    @Override
    public BasicCase reflactCaze(String caseName) {
        if(caseName != null && !caseName.equals("")){
            //拼接字符串，注意类名包含包名
            String className = "com.github.qingquanlv.testflow_service_api.service.impl.cazes." + caseName;
            try {
                //获取以className字符串为名的类
                Class<?> cazeImplClass
                    = Class.forName(className);//返回类的实例
                return (BasicCase) cazeImplClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public BasicCase reflactCaze(String caseName, Object obj) {
        if(caseName != null && !caseName.equals("")){
            //拼接字符串，注意类名包含包名
            String className = "com.github.qingquanlv.testflow_service_api.service.impl.cazes." + caseName;
            try {
                //获取以className字符串为名的类
                Class<?> cazeImplClass
                    = Class.forName(className);//返回类的实例
                Constructor<?> constructor = cazeImplClass.getConstructor(Object.class);
                return (BasicCase) constructor.newInstance(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
