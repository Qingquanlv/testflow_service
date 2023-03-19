package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.TaskDetails;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.IndexResult;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.StepResult;
import com.github.qingquanlv.testflow_service_api.entity.job.taskstepresult.GetTaskStepResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.taskstepresult.GetTaskStepResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.*;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: qingquan.lv
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TaskServiceImpl implements TaskService {

        @Autowired
        FeatureMapper featureMapper;

        @Autowired
        private JobMapper jobMapper;

        @Autowired
        TaskMapper taskMapper;

        @Autowired
        private TaskResultMapper taskResultMapper;

        @Autowired
        ParameterIndexStepResultMapper parameterIndexStepResultMapper;


        /**
         * Get Task By Job Id
         *
         * @param request
         * @return
         */
        @Override
        public QueryJobResponse getTask(QueryJobRequest request) {
                Status status = new Status();
                status.setSuccess(true);
                String filter
                        = null == request.getFilter() ? "" : request.getFilter();
                long pageNum
                        = null == request.getPageNum() ? 1L : request.getPageNum();
                long pageSize
                        = null == request.getPageSize() ? 10L : request.getPageSize();
                Page<Task> taskList = new Page<>();
                if (null == request.getJobId()) {
                        taskList
                                = taskMapper.selectPage(
                                new Page<Task>(pageNum, pageSize),
                                Wrappers.<Task>lambdaQuery()
                                        .like(Task::getParameterName, filter)
                                        .orderByDesc(Task::getId));
                }
                else {
                        taskList
                                = taskMapper.selectPage(
                                new Page<Task>(pageNum, pageSize),
                                Wrappers.<Task>lambdaQuery()
                                        .eq(Task::getJobId, request.getJobId())
                                        .like(Task::getParameterName, filter)
                                        .orderByDesc(Task::getId));
                }
                //赋值taskResult
                List<TaskDetails> list = new ArrayList<>();
                if (!CollectionUtils.isEmpty(taskList.getRecords())) {
                        for (Task task : taskList.getRecords()) {
                                Job job = jobMapper.selectById(task.getJobId());
                                if (null == job) {
                                        continue;
                                }
                                FeatureTab feature = featureMapper.selectById(job.getFeatureId());
                                if (null == feature) {
                                        continue;
                                }
                                TaskDetails taskDetails
                                        = TaskDetails.builder()
                                        .id(task.getId())
                                        .jobId(task.getJobId())
                                        .jobName(job.getName())
                                        .featureId(job.getFeatureId())
                                        .featureName(feature.getFeature_name())
                                        .startTime(task.getStarttime())
                                        .endTime(task.getEndtime())
                                        .status(task.getStatus())
                                        .paramName(job.getParam_name())
                                        .paramIndex(job.getParamIndexId()).build();
                                list.add(taskDetails);
                        }
                }
                QueryJobResponse rsp
                        = QueryJobResponse.builder()
                        .status(status)
                        .jobDetails(list)
                        .total(taskList.getTotal()).build();
                return rsp;
        }

        /**
         * Get Task Result
         *
         * @param request
         * @return
         */
        @Override
        public GetTaskResultResponse getTaskResult(GetTaskResultRequest request) {
                Status status = new Status();
                status.setSuccess(true);
                Integer pass = 0;
                Integer fail = 0;
                String filter
                        = null == request.getFilter() ? "" : request.getFilter();
                Timestamp startTime
                        = null == request.getStartTime() ? new Timestamp(0) : request.getStartTime();
                Timestamp endTime
                        = null == request.getStartTime() ? new Timestamp(new Long(Integer.MAX_VALUE) * 1000) : request.getStartTime();
                Integer pageNum
                        = null == request.getPageNum() ? 1 : request.getPageNum();
                Integer pageSize
                        = null == request.getPageSize() ? 5 : request.getPageSize();
                Integer total = 0;
                GetTaskResultResponse rsp = new GetTaskResultResponse();
                List<TaskResult> taskResults;
                if (null == request.getStatus() || 2 == request.getStatus()) {
                        taskResults = taskResultMapper.selectList(
                                Wrappers.<TaskResult>lambdaQuery()
                                        .eq(TaskResult::getTaskId, request.getTaskId())
                                        .gt(TaskResult::getStarttime, startTime)
                                        .lt(TaskResult::getEndtime, endTime));
                }
                else {
                        taskResults = taskResultMapper.selectList(
                                Wrappers.<TaskResult>lambdaQuery()
                                        .eq(TaskResult::getTaskId, request.getTaskId())
                                        .eq(TaskResult::getStatus, request.getStatus())
                                        .gt(TaskResult::getStarttime, startTime)
                                        .lt(TaskResult::getEndtime, endTime));
                }
                List<IndexResult> indexResults = new ArrayList<>();
                if (null != taskResults && !CollectionUtils.isEmpty(taskResults)) {
                        //计算pass，fail case总数
                        pass = taskResults
                                .stream()
                                .anyMatch(item->1 == item.getStatus())
                                ? taskResults
                                .stream()
                                .filter(item->1 == item.getStatus()).collect(Collectors.toList()).size()
                                : 0;
                        fail = taskResults
                                .stream()
                                .anyMatch(item->0 == item.getStatus())
                                ? taskResults
                                .stream()
                                .filter(item->0 == item.getStatus()).collect(Collectors.toList()).size()
                                : 0;
                        int toIndex = Math.min(pageSize * pageNum, taskResults.size());
                        int fromIndex = Math.max(0, pageSize * (pageNum- 1));
                        total = taskResults.size();
                        if (!CollectionUtils.isEmpty(taskResults)) {
                                for (TaskResult taskResult : taskResults.subList(fromIndex, toIndex)) {
                                        IndexResult indexResult = new IndexResult();
                                        indexResult.setParamIndex(taskResult.getParameter_value_index());
                                        indexResult.setParamValue(taskResult.getParameter_value());
                                        indexResult.setParamName(taskResult.getParameter_name());
                                        indexResult.setStatus(taskResult.getStatus());
                                        indexResult.setStartTime(taskResult.getStarttime());
                                        indexResult.setEndTime(taskResult.getEndtime());
                                        List<ParameterIndexStepResult> caseInfos
                                                = parameterIndexStepResultMapper.selectList(Wrappers.<ParameterIndexStepResult>lambdaQuery()
                                                .eq(ParameterIndexStepResult::getTaskResultId, taskResult.getId()));
                                        List<StepResult> stepResults = new ArrayList<>();
                                        for (ParameterIndexStepResult result : caseInfos) {
                                                StepResult stepResult
                                                        = StepResult.builder()
                                                        .id(result.getTaskResultId())
                                                        .label(result.getLabel())
                                                        .clazz(result.getClazz())
                                                        .status(result.getStatus())
                                                        .config(result.getConfig())
                                                        .data(result.getData()).build();
                                                stepResults.add(stepResult);
                                        }
                                        indexResult.setStepResults(stepResults);

                                        indexResults.add(indexResult);
                                }
                        }
                }
                rsp.setStatus(status);
                rsp.setIndexResults(indexResults);
                rsp.setPass(pass);
                rsp.setFail(fail);
                rsp.setTotal(total);
                return rsp;
        }

        /**
         * Get Task Step Result
         *
         * @param request
         * @return
         */
        @Override
        public GetTaskStepResultResponse getTaskStepResult(GetTaskStepResultRequest request) {
                GetTaskStepResultResponse response = GetTaskStepResultResponse.builder().build();
                ParameterIndexStepResult resultIndexInfo
                        = parameterIndexStepResultMapper.selectOne(Wrappers.<ParameterIndexStepResult>lambdaQuery()
                        .eq(ParameterIndexStepResult::getTaskResultId, request.getTaskResultId())
                        .eq(ParameterIndexStepResult::getResultIndexId, request.getResultIndexId()));
                if (null != resultIndexInfo) {
                        response
                                = GetTaskStepResultResponse.builder()
                                .id(resultIndexInfo.getId())
                                .clazz(resultIndexInfo.getClazz())
                                .status(resultIndexInfo.getStatus())
                                .label(resultIndexInfo.getLabel())
                                .config(resultIndexInfo.getConfig())
                                .data(resultIndexInfo.getData())
                                .build();
                }
                return response;
        }
}
