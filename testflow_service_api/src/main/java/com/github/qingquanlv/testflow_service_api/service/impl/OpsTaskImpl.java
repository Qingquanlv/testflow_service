package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist.GetOpsTaskListRequest;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist.GetOpsTaskListResponse;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist.OpsTaskDetails;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult.GetOpsTaskResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult.GetOpsTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult.OpsPlanTaskResultDetails;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.OpsPlan;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.OpsPlanTask;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.OpsPlanTaskResult;
import com.github.qingquanlv.testflow_service_api.mapper.OpsPlanMapper;
import com.github.qingquanlv.testflow_service_api.mapper.OpsPlanTaskMapper;
import com.github.qingquanlv.testflow_service_api.mapper.OpsPlanTaskResultMapper;
import com.github.qingquanlv.testflow_service_api.service.OpsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Service
public class OpsTaskImpl implements OpsTaskService {

        @Autowired
        OpsPlanTaskResultMapper opsPlanTaskResultMapper;

        @Autowired
        OpsPlanTaskMapper opsPlanTaskMapper;

        @Autowired
        OpsPlanMapper opsPlanMapper;

        /**
         * Get all task
         *
         * @param request
         * @return
         */
        @Override
        public GetOpsTaskListResponse getTask(GetOpsTaskListRequest request) {
                Status status = new Status();
                status.setSuccess(true);
                String filter
                        = null == request.getFilter() ? "" : request.getFilter();
                long pageNum
                        = null == request.getPageNum() ? 1L : request.getPageNum();
                long pageSize
                        = null == request.getPageSize() ? 10L : request.getPageSize();
                //query job
                Page<OpsPlanTask> tasks
                        = opsPlanTaskMapper.selectPage(
                        new Page<OpsPlanTask>(pageNum, pageSize),
                        Wrappers.<OpsPlanTask>lambdaQuery()
                                .like(OpsPlanTask::getPlan_id, filter)
                                .orderByDesc(OpsPlanTask::getId));
                //query all plan
                List<OpsPlan> opsPlans = opsPlanMapper.selectList(Wrappers.emptyWrapper());
                List<OpsTaskDetails> opsPlanTaskList = new ArrayList<>();
                for (OpsPlanTask item : tasks.getRecords()) {
                        OpsPlan opsPlan = opsPlans.stream()
                                .filter(i->item.getPlan_id().equals(i.getId()))
                                .findFirst().orElse(null);
                        OpsTaskDetails opsTaskDetails
                                = OpsTaskDetails.builder()
                                .id(item.getId())
                                .planId(item.getPlan_id())
                                .planName(opsPlan.getOpsPlanName())
                                .startTime(item.getStarttime())
                                .endTime(item.getEndtime())
                                .status(item.getStatus()
                                ).build();
                        opsPlanTaskList.add(opsTaskDetails);
                }
                GetOpsTaskListResponse rsp
                        = GetOpsTaskListResponse.builder()
                        .opsTaskDetails(opsPlanTaskList)
                        .total(tasks.getTotal())
                        .status(status)
                        .build();
                return rsp;
        }

        /**
         * Get task results by task id
         *
         * @param request
         * @return
         */
        @Override
        public GetOpsTaskResultResponse getTaskStepResult(GetOpsTaskResultRequest request) {
                Status status = new Status();
                status.setSuccess(true);
                List<OpsPlanTaskResult> opsPlanTaskResult
                        = opsPlanTaskResultMapper.selectList(Wrappers.<OpsPlanTaskResult>lambdaQuery()
                        .eq(OpsPlanTaskResult::getTaskId, request.getOpsTaskId()));
                List<OpsPlanTaskResultDetails> opsPlanTaskResultDetails
                        = new ArrayList<>();
                for (OpsPlanTaskResult item : opsPlanTaskResult) {
                        OpsPlanTaskResultDetails opsPlanTaskResultDetail
                                = OpsPlanTaskResultDetails.builder()
                                .id(item.getId())
                                .clazz(item.getClazz())
                                .label(item.getLabel())
                                .status(item.getStatus())
                                .startTime(item.getStarttime())
                                .endTime(item.getEndtime())
                                .config(item.getConfig())
                                .data(item.getData())
                                .build();
                        opsPlanTaskResultDetails.add(opsPlanTaskResultDetail);
                }
                GetOpsTaskResultResponse response
                        = GetOpsTaskResultResponse.builder()
                        .status(status)
                        .opsPlanTaskResultDetails(opsPlanTaskResultDetails)
                        .total(opsPlanTaskResult.size())
                        .build();
                return response;

        }
}
