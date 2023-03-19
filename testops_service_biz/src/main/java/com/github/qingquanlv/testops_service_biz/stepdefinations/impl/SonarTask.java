package com.github.qingquanlv.testops_service_biz.stepdefinations.impl;

import com.github.qingquanlv.testops_service_biz.common.Constants;
import com.github.qingquanlv.testops_service_biz.entity.TaskResult;
import com.github.qingquanlv.testops_service_biz.stepdefinations.BaseTask;
import com.github.qingquanlv.testops_service_biz.utilities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/11/21 下午6:31
 */
public class SonarTask extends BaseTask {

        @Override
        public TaskResult exec(String taskParameter) {
                TaskResult result = new TaskResult();
                try {
                        List<String> commandList = new ArrayList<>();
                        commandList.add("mvn");
                        commandList.add("clean");
                        commandList.add("verify");
                        commandList.add("sonar:sonar");
                        commandList.add("-Dmaven.test.skip=true");
                        commandList.add("-Dsonar.login=e38ecbca89bac5a83373f674d70e0b37916459ed");
                        result.setStatus(1);
                        result.setResult(ParamUtil.getSonarResultUrl(ShellUtil.run(commandList, "ababa")));
                }
                catch (Exception ex) {
                        ex.printStackTrace();
                }
                return result;
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
