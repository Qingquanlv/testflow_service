package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.CaseInfo;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.TaskDetails;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.IndexResult;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.StepResult;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Job;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Task;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.TaskResult;
import com.github.qingquanlv.testflow_service_api.mapper.FeatureMapper;
import com.github.qingquanlv.testflow_service_api.mapper.JobMapper;
import com.github.qingquanlv.testflow_service_api.mapper.TaskMapper;
import com.github.qingquanlv.testflow_service_api.mapper.TaskResultMapper;
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
        TaskMapper taskMapper;

        @Autowired
        private TaskResultMapper taskResultMapper;


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
                List<Task> taskList = new ArrayList<>();
                if (null == request.getJobId()) {
                        taskList
                                = taskMapper.selectList(Wrappers.emptyWrapper());
                }
                else {
                        taskList
                                = taskMapper.selectList(
                                Wrappers.<Task>lambdaQuery()
                                        .eq(Task::getJobId, request.getJobId()));

                }

                //赋值taskResult
                List<TaskDetails> list = new ArrayList<>();
                if (!CollectionUtils.isEmpty(taskList)) {
                        for (Task task : taskList) {
                                Job job = jobMapper.selectById(task.getJobId());
                                Feature feature = featureMapper.selectById(job.getFeatureId());
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
                                        indexResult.setParamIndex(index);
                                        indexResult.setParamValue(taskResult.getParameter_value());
                                        indexResult.setParamName(taskResult.getParameter_name());
                                        indexResult.setStatus(taskResult.getStatus());
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
