package com.github.qingquanlv.testops_service_biz.utilities;

import com.github.qingquanlv.testops_service_biz.common.Constants;

/**
 * @Author: qingquan.lv
 * @Date: 2021/11/21 下午6:56
 */
public class Util {

    public static String getClassName(String taskType) {
        String path ="";
        switch (taskType) {
            case Constants.SONAR: {
                path = "com.github.qingquanlv.testops_service_biz.stepdefinations.impl.SonarTask";
                break;
            }
            default: {}
        }
        return path;
    }
}
