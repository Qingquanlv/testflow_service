package com.github.qingquanlv.testops_service_biz.stepdefinations;

import com.github.qingquanlv.testops_service_biz.entity.TaskResult;

/**
 * @Author: qingquan.lv
 * @Date: 2021/11/20 下午12:11
 */
public interface Execute {

    public TaskResult exec(String taskParameter);
}
