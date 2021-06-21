package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.task.gettaskresults.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.task.gettaskresults.TaskStepResult;
import com.github.qingquanlv.testflow_service_api.entity.task.gettaskstepresult.GetTaskStepResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.FeatureResult;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Task;
import com.github.qingquanlv.testflow_service_api.mapper.FeatureMapper;
import com.github.qingquanlv.testflow_service_api.mapper.FeatureResultMapper;
import com.github.qingquanlv.testflow_service_api.mapper.TaskMapper;
import com.github.qingquanlv.testflow_service_api.service.TaskService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 19:50
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TaskServiceImpl implements TaskService {

        @Autowired
        FeatureResultMapper featureResultMapper;

        @Autowired
        FeatureMapper featureMapper;

        @Autowired
        TaskMapper taskMapper;

        /**
         *
         *
         * @param taskId
         * @return
         */
        @Override
        public GetTaskResultResponse getTaskResult(Long taskId) {
                Status status = new Status();
                status.setSuccess(true);
                GetTaskResultResponse rsp = new GetTaskResultResponse();
                List<TaskStepResult> taskStepResults = new ArrayList<>();
                List<FeatureResult> featureResults = featureResultMapper.selByTid(taskId);
                rsp.setStatus(status);
                if  (!CollectionUtils.isEmpty(featureResults)) {
                        //获取feature name
                        Feature feature = featureMapper.selectById(featureResults.get(0).getFeature_id());
                        rsp.setFeatureName(null != feature ? feature.getFeature_name() : "");
                        rsp.setTaskId(featureResults.get(0).getTask_id());
                        //获取task name
                        Task task = taskMapper.selectById(featureResults.get(0).getTask_id());
                        rsp.setTaskName(null != task ? task.getName() : "");
                        rsp.setFeatureId(featureResults.get(0).getFeature_id());
                        // set step result
                        for (FeatureResult featureResult : featureResults) {
                                TaskStepResult taskStepResult = new TaskStepResult();
                                taskStepResult.setStepId(featureResult.getParameter_value_index());
                                taskStepResult.setAssertions(featureResult.getAssertions());
                                taskStepResult.setLogs(featureResult.getLogs());
                                taskStepResults.add(taskStepResult);
                        }
                }
                rsp.setTaskStepResults(taskStepResults);
                return rsp;
        }

        /**
         * 获取task step结果
         *
         * @param taskId
         * @param stepId
         * @return
         */
        @Override
        public GetTaskStepResultResponse getTaskStepResult(Long taskId, Long stepId) {
                TestFlowManager testFlowManager = new TestFlowManager();
                Status status = new Status();
                status.setSuccess(true);
                GetTaskStepResultResponse rsp = new GetTaskStepResultResponse();
                FeatureResult featureResult = featureResultMapper.selByTidSid(taskId, stepId);
                rsp.setStatus(status);
                rsp.setFeatureId(featureResult.getFeature_id());
                //获取feature name
                Feature feature = featureMapper.selectById(featureResult.getFeature_id());
                rsp.setFeatureName(null != feature ? feature.getFeature_name() : "");
                rsp.setTaskId(featureResult.getTask_id());
                //获取task name
                Task task = taskMapper.selectById(featureResult.getTask_id());
                rsp.setTaskName(null != feature ? task.getName() : "");
                rsp.setStepId(stepId);
                rsp.setAssertions(featureResult.getAssertions());
                rsp.setLogs(featureResult.getLogs());
                return rsp;
        }
}
