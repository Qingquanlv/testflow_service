package com.github.qingquanlv.testops_service_biz.stepdefinations.impl;

import com.github.qingquanlv.testops_service_biz.common.Constants;
import com.github.qingquanlv.testops_service_biz.entity.TaskResult;
import com.github.qingquanlv.testops_service_biz.stepdefinations.BaseTask;
import com.github.qingquanlv.testops_service_biz.utilities.FastJsonUtil;
import com.github.qingquanlv.testops_service_biz.utilities.HttpClientUtil;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 * @Date: 2022/1/20 下午8:08
 */
public class TestFlowTask extends BaseTask {

        @Override
        public TaskResult exec(String taskParameter) {
                TaskResult taskResult
                        = new TaskResult();
                HashMap<String, String> parameter
                        = FastJsonUtil.toMap(taskParameter);
                String url
                        = String.format("%s%s",
                        Constants.TEST_FLOW_TASK_URL,
                        parameter.get(Constants.JOB_ID));
                taskResult.setResult(HttpClientUtil.sendHttpGet(url));
                taskResult.setStatus(1);
                return taskResult;
        }

        @Override
        public TaskResult result(String taskParameter) {
                TaskResult result = new TaskResult();
                HashMap<String, String> parameter
                        = FastJsonUtil.toMap(taskParameter);
                String url
                        = String.format("%s%s",
                        Constants.TEST_FLOW_TASK_URL,
                        parameter.get(Constants.JOB_ID));
                result.setResult(HttpClientUtil.sendHttpGet(url));
                return result;
        }

        @Override
        public void revert() {

        }
}

