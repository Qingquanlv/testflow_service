package com.github.qingquanlv.testops_service_biz;

import com.github.qingquanlv.testops_service_biz.entity.TaskResult;
import com.github.qingquanlv.testops_service_biz.stepdefinations.BaseTask;
import com.github.qingquanlv.testops_service_biz.utilities.Util;

/**
 * @Author: qingquan.lv
 * @Date: 2021/11/20 上午11:13
 */
public class TestOpsManager {

    public TaskResult result(String taskType, String taskParameter) {
        TaskResult result = new TaskResult();
        try {
            BaseTask task
                    = (BaseTask)Class
                    .forName(Util.getClassName(taskType))
                    .newInstance();
            result = task.result(taskParameter);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public TaskResult execTask(String taskType, String taskParameter) {
        TaskResult result = new TaskResult();
        try {
            BaseTask task
                    = (BaseTask)Class
                    .forName(Util.getClassName(taskType))
                    .newInstance();
            result = task.exec(taskParameter);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
