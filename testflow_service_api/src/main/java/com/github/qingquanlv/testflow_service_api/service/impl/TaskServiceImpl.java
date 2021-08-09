package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.CaseInfo;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.TaskDetails;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.IndexResult;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.StepResult;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Jobb;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.TaskResult;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.service.TaskService;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 19:50
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TaskServiceImpl implements TaskService {

        @Autowired
        FeatureMapper featureMapper;

        @Autowired
        private JobMapper jobMapper;

        @Autowired
        private TaskResultMapper taskResultMapper;

        /**
         * Get Task By Job Id
         *
         * @param jobId
         * @return
         */
        @Override
        public QueryJobResponse getTaskByJobId(Long jobId) {
                Status status = new Status();
                status.setSuccess(true);
                Jobb job = jobMapper.selectById(jobId);
                Feature feature = featureMapper.selectById(job.getFeatureId());
                List<TaskResult> taskResultList
                        = taskResultMapper.selectList(
                        Wrappers.<TaskResult>lambdaQuery()
                                .eq(TaskResult::getTaskId, jobId)
                                .in(TaskResult::getParameter_value_index, Utils.toListLong(job.getParamIndexId())));
                //赋值taskResult
                List<TaskDetails> list = new ArrayList<>();
                List<Long> indexList
                        = Utils.toListLong(job.getParamIndexId());
                if (!CollectionUtils.isEmpty(indexList)) {
                        for (Long index : indexList) {
                                List<TaskResult> taskList = taskResultList.stream()
                                        .filter(item -> index.equals(item.getParameter_value_index()))
                                        .collect(Collectors.toList());
                                if (!CollectionUtils.isEmpty(taskList)) {
                                        Integer taskStatus = 1;
                                        TaskDetails taskDetails
                                                = TaskDetails.builder()
                                                .jobId(jobId)
                                                .jobName(job.getName())
                                                .featureId(job.getFeatureId())
                                                .featureName(feature.getFeature_name())
                                                .paramName(job.getParam_name())
                                                .paramIndex(job.getParamIndexId()).build();
                                        for (TaskResult item : taskList) {
                                                taskDetails.setId(item.getTaskId());
                                                taskDetails.setStartTime(
                                                        null == taskDetails.getStartTime() ||
                                                                item.getStarttime().after(taskDetails.getStartTime()) ?
                                                                taskDetails.getStartTime() :
                                                                item.getStarttime());
                                                taskDetails.setEndTime(
                                                        null == taskDetails.getEndTime() ||
                                                                item.getEndtime().before(taskDetails.getEndTime()) ?
                                                                taskDetails.getEndTime() :
                                                                item.getEndtime());
                                                taskDetails.setStatus(
                                                        taskStatus & taskDetails.getStatus());
                                        }
                                        list.add(taskDetails);
                                }
                        }
                }
                QueryJobResponse rsp
                        = QueryJobResponse.builder()
                        .status(status)
                        .jobDetails(list).build();
                return rsp;
        }

        /**
         * Get Task Result
         *
         * @param taskId
         * @return
         */
        @Override
        public GetTaskResultResponse getTaskResult(Long taskId) {
                Status status = new Status();
                status.setSuccess(true);
                GetTaskResultResponse rsp = new GetTaskResultResponse();
                List<TaskResult> taskResults = taskResultMapper.selectList(
                        Wrappers.<TaskResult>lambdaQuery()
                                .eq(TaskResult::getTaskId, taskId));
                List<IndexResult> indexResults = new ArrayList<>();
                if (!CollectionUtils.isEmpty(taskResults)) {
                        Set<Long> indexList
                                = taskResults.stream().map(TaskResult::getParameter_value_index)
                                .collect(Collectors.toSet());
                        if (!CollectionUtils.isEmpty(indexList)) {
                                for (Long index : indexList) {
                                        IndexResult indexResult = new IndexResult();
                                        TaskResult taskResult
                                                = taskResults
                                                .stream()
                                                .filter(item->
                                                        index.equals(item.getParameter_value_index()))
                                                .findFirst().orElse(null);
                                        indexResult.setIndex(index);
                                        indexResult.setStartTime(taskResult.getStarttime());
                                        indexResult.setEndTime(taskResult.getEndtime());
                                        if (null != taskResult) {
                                                List<StepResult> stepResults = new ArrayList<>();
                                                List<CaseInfo> caseInfos
                                                        = FastJsonUtil.toListBean(
                                                        taskResult.getLogs(), CaseInfo.class);
                                                Long id = 0L;
                                                for (CaseInfo result : caseInfos) {
                                                        StepResult stepResult
                                                                = StepResult.builder()
                                                                .id(id)
                                                                .label(result.getLabel())
                                                                .clazz(result.getClazz())
                                                                .status(result.getStatus())
                                                                .config(result.getConfig())
                                                                .data(result.getData()).build();
                                                        stepResults.add(stepResult);
                                                        id ++;
                                                }
                                                indexResult.setStepResults(stepResults);
                                        }
                                        indexResults.add(indexResult);
                                }
                        }
                }
                rsp.setStatus(status);
                rsp.setIndexResults(indexResults);
                return rsp;
        }
}
