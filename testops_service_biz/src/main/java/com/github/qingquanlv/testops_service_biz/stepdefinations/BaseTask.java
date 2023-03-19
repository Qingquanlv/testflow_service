package com.github.qingquanlv.testops_service_biz.stepdefinations;

import com.github.qingquanlv.testops_service_biz.entity.TaskResult;

/**
 * @Author: qingquan.lv
 * @Date: 2021/11/20 下午12:09
 */
public abstract class BaseTask implements Execute, Revert, Result{

    @Override
    public abstract TaskResult exec(String taskParameter);

    @Override
    public abstract void revert();

    @Override
    public abstract TaskResult result(String taskParameter);

}
