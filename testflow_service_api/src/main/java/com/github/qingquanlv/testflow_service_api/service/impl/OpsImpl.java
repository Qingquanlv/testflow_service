package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.qingquanlv.testflow_service_api.common.Constants;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.ops.Config;
import com.github.qingquanlv.testflow_service_api.entity.ops.Edges;
import com.github.qingquanlv.testflow_service_api.entity.ops.Nodes;
import com.github.qingquanlv.testflow_service_api.entity.ops.createtestplan.CreateTestPlanRequest;
import com.github.qingquanlv.testflow_service_api.entity.ops.createtestplan.CreateTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.deletetestplan.DeleteTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan.QueryAllTestPlanRequest;
import com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan.QueryAllTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan.QueryTestPlan;
import com.github.qingquanlv.testflow_service_api.entity.ops.querytestplan.QueryTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.*;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.service.IOpsService;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import com.github.qingquanlv.testops_service_biz.TestOpsManager;
import com.github.qingquanlv.testops_service_biz.entity.TaskResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: qingquan.lv
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OpsImpl implements IOpsService {

    @Autowired
    OpsPlanMapper opsPlanMapper;

    @Autowired
    OpsTaskMapper opsTaskMapper;

    @Autowired
    OpsPlanTaskMapper opsPlanTaskMapper;

    @Autowired
    OpsPlanTaskResultMapper opsPlanTaskResultMapper;

    @Autowired
    OpsTaskNextTaskMapper opsTaskNextTaskMapper;

    @Autowired
    OpsTestflowTaskConfigMapper opsTestflowTaskConfigMapper;

    @Autowired
    OpsSonarTaskConfigMapper opsSonarTaskConfigMapper;


    private List<Long> executeTaskQuery = new ArrayList<>();


    @Override
    public CreateTestPlanResponse createTestPlan(CreateTestPlanRequest request) {
        CreateTestPlanResponse response = new CreateTestPlanResponse();
        Status status = new Status();
        status.setSuccess(true);
        OpsPlan opsPlan =
                OpsPlan.builder()
                        .opsPlanName(request.getTestPlanName())
                        .description(request.getDescription())
                        .build();
        //if feature exist, update feature
        if (null != request.getTestPlanId()
                && null != opsPlanMapper.selectById(request.getTestPlanId())) {
            opsPlan.setId(request.getTestPlanId());
            opsPlanMapper.updateById(opsPlan);
        } else {
            opsPlanMapper.insert(opsPlan);
        }
        List<OpsTask> tasks = new ArrayList<>();
        //遍历node信息，先删除在插入node和config信息
        if (!CollectionUtils.isEmpty(request.getNodes())) {
            List<OpsTask> opsTaskList = opsTaskMapper.selectList(
                    Wrappers.<OpsTask>lambdaQuery()
                            .in(OpsTask::getTaskPlanId, opsPlan.getId()));
            if (!CollectionUtils.isEmpty(opsTaskList)) {
                List<Long> taskTaskIds = opsTaskList
                        .stream()
                        .map(OpsTask::getId)
                        .collect(Collectors.toList());
                opsTestflowTaskConfigMapper.delete(
                        Wrappers.<OpsTestflowTaskConfig>lambdaQuery()
                                .in(OpsTestflowTaskConfig::getOpsTaskId, taskTaskIds));
                opsSonarTaskConfigMapper.delete(
                        Wrappers.<OpsSonarTaskConfig>lambdaQuery()
                                .in(OpsSonarTaskConfig::getOpsTaskId, taskTaskIds));
                //opsTask的连线信息，直接删除
                List<OpsTask> opsTasks
                        = opsTaskMapper.selectList(Wrappers.<OpsTask>lambdaQuery()
                        .eq(OpsTask::getTaskPlanId, opsPlan.getId()));
                Set<Long> opsTaskIds = opsTasks
                        .stream()
                        .map(OpsTask::getId)
                        .collect(Collectors.toSet());
                opsTaskNextTaskMapper.delete(
                        Wrappers.<OpsTaskNextTask>lambdaQuery()
                                .in(OpsTaskNextTask::getSourceTaskId, opsTaskIds)
                                .or()
                                .in(OpsTaskNextTask::getTargetTaskId, opsTaskIds));
                //opsTask数据全删全插
                opsTaskMapper.delete(Wrappers.<OpsTask>lambdaQuery()
                        .eq(OpsTask::getTaskPlanId, opsPlan.getId()));
            }
            for (Nodes node : request.getNodes()) {
                OpsTask opsTask =
                        OpsTask.builder()
                                .label(node.getLabel())
                                .taskPlanId(opsPlan.getId())
                                .taskType(node.getClazz())
                                .x(node.getX())
                                .y(node.getY())
                                .build();
                opsTaskMapper.insert(opsTask);
                insertCaseConfig(request,
                        node.getClazz(),
                        opsTask.getId(),
                        opsTask.getLabel());
                tasks.add(opsTask);
            }
            //插入连线信息
            if (!CollectionUtils.isEmpty(request.getEdges())) {
                for (Edges edge : request.getEdges()) {
                    //判断当前线的源节点和目标节点存在
                    if (!CollectionUtils.isEmpty(request.getNodes())
                            && tasks
                            .stream()
                            .anyMatch(item ->
                                    item.getLabel().equals(edge.getSource()))
                            && tasks
                            .stream()
                            .anyMatch(item ->
                                    item.getLabel().equals(edge.getSource()))) {
                        OpsTask sourceCase = tasks.stream()
                                .filter(item -> edge.getSource().equals(item.getLabel()))
                                .findFirst().orElse(null);
                        OpsTask targetCase = tasks.stream()
                                .filter(item -> edge.getTarget().equals(item.getLabel()))
                                .findFirst().orElse(null);
                        OpsTaskNextTask opsTaskNextTask =
                                OpsTaskNextTask
                                        .builder()
                                        .sourceTaskId(sourceCase.getId())
                                        .targetTaskId(targetCase.getId())
                                        .sourceAnchor(edge.getSourceAnchor())
                                        .targetAnchor(edge.getTargetAnchor())
                                        .build();
                        opsTaskNextTaskMapper.insert(opsTaskNextTask);
                    }
                }
            }
        }
        response.setTestPlanId(null == opsPlan ? null : opsPlan.getId());
        response.setStatus(status);
        return response;
    }


    private void insertCaseConfig(CreateTestPlanRequest request, String type, Long taskId, String taskLabel) {
        //判断相关case的配置在request中是否存在，若存在插入db
        if (!CollectionUtils.isEmpty(request.getConfig())
                && null != taskLabel
                && null != request.getConfig()
                .stream()
                .filter(item -> taskLabel.equals(item.getLabel()))
                .findFirst().orElse(null)) {
            Config config = request.getConfig()
                    .stream()
                    .filter(item -> taskLabel.equals(item.getLabel()))
                    .findFirst().orElse(null);
            if (Constants.TESTFLOW.equals(type)) {
                OpsTestflowTaskConfig opsTestflowTaskConfig =
                        OpsTestflowTaskConfig.builder(
                        ).opsTaskId(taskId
                        ).taskflowTaskId(
                                Long.valueOf(config.getExec_params()
                                .get("taskflowTaskId"))
                        ).build();
                opsTestflowTaskConfigMapper.insert(opsTestflowTaskConfig);
            }
            else if (Constants.SONAR.equals(type)) {
                OpsSonarTaskConfig opsSonarTaskConfig =
                        OpsSonarTaskConfig.builder(
                        ).opsTaskId(taskId
                        ).sonarTaskId(
                                Long.valueOf(null != config.getExec_params()
                                        .get("sonarTaskId") ? config.getExec_params()
                                        .get("sonarTaskId") : "0")
                        ).repoUrl(
                                config.getExec_params()
                                        .get("repoRrl")
                        ).build();
                opsSonarTaskConfigMapper.insert(opsSonarTaskConfig);
            }
        }
    }


    @Async("opsTaskExecutor")
    @Override
    public void execTestPlan(Long planId) {
        //executeTaskQuery.add(planId);
        execOpsTask(planId);
    }

    @Override
    public DeleteTestPlanResponse deleteTestPlan(Long planId) {
        Status status = new Status();
        status.setSuccess(true);
        //根据featureId查询所有case
        List<OpsTask> opsTaskList
                = opsTaskMapper.selectList(Wrappers.<OpsTask>lambdaQuery()
                .eq(OpsTask::getTaskPlanId, planId));
        if (!CollectionUtils.isEmpty(opsTaskList)) {
            List<Long> taskIds = opsTaskList
                    .stream()
                    .map(OpsTask::getId).collect(Collectors.toList());
            opsTestflowTaskConfigMapper.delete(
                    Wrappers.<OpsTestflowTaskConfig>lambdaQuery()
                            .in(OpsTestflowTaskConfig::getOpsTaskId, taskIds));
            opsSonarTaskConfigMapper.delete(
                    Wrappers.<OpsSonarTaskConfig>lambdaQuery()
                            .in(OpsSonarTaskConfig::getOpsTaskId, taskIds));
            opsTaskMapper.delete(
                    Wrappers.<OpsTask>lambdaQuery()
                            .in(OpsTask::getId, taskIds));
        }
        opsPlanMapper.delete(
                Wrappers.<OpsPlan>lambdaQuery()
                        .eq(OpsPlan::getId, planId));
        DeleteTestPlanResponse rsp
                = DeleteTestPlanResponse.builder()
                .status(status)
                .build();
        return rsp;
    }

    @Override
    public QueryTestPlanResponse getTestPlan(Long id) {
        OpsPlan opsPlan = opsPlanMapper.selectById(id);
        //根据featureId查询所有case
        List<OpsTask> opsTaskList
                = opsTaskMapper.selectList(Wrappers.<OpsTask>lambdaQuery()
                .eq(OpsTask::getTaskPlanId, id));
        List<com.github.qingquanlv.testflow_service_api.entity.ops.Nodes> nodes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(opsTaskList)) {
            for (OpsTask opsTask : opsTaskList) {
                com.github.qingquanlv.testflow_service_api.entity.ops.Nodes node = com.github.qingquanlv.testflow_service_api.entity.ops.Nodes.builder()
                        .id(opsTask.getId())
                        .label(opsTask.getLabel())
                        .clazz(opsTask.getTaskType())
                        .x(opsTask.getX())
                        .y(opsTask.getY())
                        .build();
                nodes.add(node);
            }
        }
        Set<Long> taskIds = nodes
                .stream()
                .map(com.github.qingquanlv.testflow_service_api.entity.ops.Nodes::getId).collect(Collectors.toSet());
        List<com.github.qingquanlv.testflow_service_api.entity.ops.Config> configs = new ArrayList<>();
        List<com.github.qingquanlv.testflow_service_api.entity.ops.Edges> edges = new ArrayList<>();

        if (!CollectionUtils.isEmpty(taskIds)) {
            //根据caseId查询所有caseConfig
            List<OpsTestflowTaskConfig> opsTestflowTaskConfigs
                    = opsTestflowTaskConfigMapper.selectList(
                    Wrappers.<OpsTestflowTaskConfig>lambdaQuery()
                            .in(OpsTestflowTaskConfig::getOpsTaskId, taskIds));
            if (!CollectionUtils.isEmpty(opsTestflowTaskConfigs)) {
                for (OpsTestflowTaskConfig opsTestflowTaskConfig : opsTestflowTaskConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    com.github.qingquanlv.testflow_service_api.entity.ops.Nodes node = nodes
                            .stream()
                            .filter(item->opsTestflowTaskConfig.getOpsTaskId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("taskflowTaskId", String.valueOf(opsTestflowTaskConfig.getTaskflowTaskId()));
                    com.github.qingquanlv.testflow_service_api.entity.ops.Config config = com.github.qingquanlv.testflow_service_api.entity.ops.Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<OpsSonarTaskConfig> opsSonarTaskConfigs
                    = opsSonarTaskConfigMapper.selectList(
                    Wrappers.<OpsSonarTaskConfig>lambdaQuery()
                            .in(OpsSonarTaskConfig::getOpsTaskId, taskIds));
            if (!CollectionUtils.isEmpty(opsSonarTaskConfigs)) {
                for (OpsSonarTaskConfig opsSonarTaskConfig : opsSonarTaskConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    com.github.qingquanlv.testflow_service_api.entity.ops.Nodes node = nodes
                            .stream()
                            .filter(item->opsSonarTaskConfig.getOpsTaskId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("sonarTaskId", String.valueOf(opsSonarTaskConfig.getOpsTaskId()));
                    params.put("repoUrl", String.valueOf(opsSonarTaskConfig.getRepoUrl()));
                    com.github.qingquanlv.testflow_service_api.entity.ops.Config config = com.github.qingquanlv.testflow_service_api.entity.ops.Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            //根据featureId查询所有连线
            List<OpsTaskNextTask> opsTaskNextTasks
                    = opsTaskNextTaskMapper.selectList(
                    Wrappers.<OpsTaskNextTask>lambdaQuery()
                            .in(OpsTaskNextTask::getSourceTaskId, taskIds));
            if (!CollectionUtils.isEmpty(opsTaskNextTasks)) {
                for (OpsTaskNextTask opsTaskNextTask : opsTaskNextTasks) {
                    com.github.qingquanlv.testflow_service_api.entity.ops.Edges edge = com.github.qingquanlv.testflow_service_api.entity.ops.Edges.builder()
                            .source(String.valueOf(opsTaskNextTask.getSourceTaskId()))
                            .target(String.valueOf(opsTaskNextTask.getTargetTaskId()))
                            .sourceAnchor(opsTaskNextTask.getSourceAnchor())
                            .targetAnchor(opsTaskNextTask.getTargetAnchor()).build();
                    edges.add(edge);
                }
            }
        }
        QueryTestPlanResponse rsp
                = QueryTestPlanResponse.builder()
                .testPlanId(id)
                .testPlanName(null == opsPlan ? "" : opsPlan.getOpsPlanName())
                .description(null == opsPlan ? "" : opsPlan.getDescription())
                .config(configs)
                .edges(edges)
                .nodes(nodes)
                .build();
        return rsp;
    }

    @Override
    public QueryAllTestPlanResponse queryTestPlanAll(QueryAllTestPlanRequest request) {
            Status status = new Status();
            status.setSuccess(true);
            String filter
                    = null == request.getFilter() ? "" : request.getFilter();
            long pageNum
                    = null == request.getPageNum() ? 1L : request.getPageNum();
            long pageSize
                    = null == request.getPageSize() ? 10L : request.getPageSize();
            Page<OpsPlan> opsPlans
                    = opsPlanMapper.selectPage(
                    new Page<OpsPlan>(pageNum, pageSize),
                    Wrappers.<OpsPlan>lambdaQuery()
                            .like(OpsPlan::getOpsPlanName, filter)
                            .orderByDesc(OpsPlan::getId));
            List<QueryTestPlan> queryFeatures = new ArrayList<>();
            for (OpsPlan opsPlan : opsPlans.getRecords()) {
                QueryTestPlan queryFeature
                        = QueryTestPlan.builder()
                        .testPlanId(opsPlan.getId())
                        .testPlanName(opsPlan.getOpsPlanName())
                        .description(opsPlan.getDescription()).build();
                queryFeatures.add(queryFeature);
            }
            QueryAllTestPlanResponse rsp =
                    QueryAllTestPlanResponse.builder()
                            .status(status)
                            .features(queryFeatures)
                            .total(opsPlans.getTotal()).build();
            return rsp;
    }

    /**
     * 轮询执行Ops task，间隔1min
     *
     * @return
     */
    public void execOpsTask(Long planTaskId) {
        //for (Long planTaskId : executeTaskQuery) {
            //获取当前时间减一个小时
            //异步逻辑暂缓
//            Timestamp taskStartTime
//                    = new Timestamp(System.currentTimeMillis()-1000*60*60);
//            OpsPlanTask opsPlanTask
//                    = opsPlanTaskMapper.selectOne(
//                    Wrappers.<OpsPlanTask>lambdaQuery()
//                            .eq(OpsPlanTask::getPlan_id, planTaskId)
//                            .eq(OpsPlanTask::getStatus, 2)
//                            .gt(OpsPlanTask::getStarttime, taskStartTime));
//            List<OpsTask> taskList;
//            if (null != opsPlanTask) {
//                //查找task下没执行成功的task
//                List<OpsPlanTask> opsPlanTaskResults
//                        = opsPlanTaskMapper.selectList(
//                        Wrappers.<OpsPlanTask>lambdaQuery()
//                                .eq(OpsPlanTask::getPlan_id, opsPlanTask.getId())
//                                .ne(OpsPlanTask::getStatus, 1));
//                Set<Long> ids = opsPlanTaskResults
//                        .stream()
//                        .map(OpsPlanTask::getId)
//                        .collect(Collectors.toSet());
//                taskList
//                        = opsTaskMapper.selectList(
//                        Wrappers.<OpsTask>lambdaQuery()
//                                .in(OpsTask::getId, ids));
//            }
//            else {
            List<OpsTask> taskList
                        = opsTaskMapper.selectList(
                        Wrappers.<OpsTask>lambdaQuery()
                                .eq(OpsTask::getTaskPlanId, planTaskId));
            Timestamp startTime
                        = new Timestamp(System.currentTimeMillis() + 60 * 60 * 8 * 1000);
            OpsPlanTask opsPlanTask
                        = OpsPlanTask
                        .builder()
                        .plan_id(planTaskId)
                        .starttime(startTime)
                        .status(2)
                        .build();
                opsPlanTaskMapper.insert(opsPlanTask);
            //}
            execPlan(taskList, opsPlanTask);
       // }
    }


    private OpsPlanTask execPlan(List<OpsTask> taskList, OpsPlanTask opsPlanResult) {
        Integer planStatus = 1;
        //储存入度
        Map<Long, Integer> inDegreeMap
                = new HashMap<>();
        initMap(taskList, inDegreeMap);
        //主要流程
        Queue<Long> s1 = new ArrayDeque<>();
        Set<Long> idSet
                = taskList
                .stream()
                .map(OpsTask::getId)
                .collect(Collectors.toSet());
        //获取入度为0的case, 并且插入队列
        idSet.removeAll(inDegreeMap.keySet());
        for (Long item : idSet) {
            s1.offer(item);
        }
        //遍历执行入度为0的case
        while (!s1.isEmpty()) {
            //抛出队头，并且执行
            Long n = s1.poll();
            OpsTask opsTask = taskList
                    .stream()
                    .filter(item -> item.getId().equals(n))
                    .findFirst().orElse(null);
            if (null == opsTask) {
                break;
            }
            Timestamp startTime
                    = new Timestamp(System.currentTimeMillis() + 60 * 60 * 8 * 1000);
            TaskResult result = executeTask(opsTask);
            Timestamp endTime
                    = new Timestamp(System.currentTimeMillis() + 60 * 60 * 8 * 1000);
            OpsPlanTaskResult taskResult
                    = OpsPlanTaskResult
                    .builder()
                    .planTaskId(opsTask.getTaskPlanId())
                    .taskId(opsPlanResult.getId())
                    .label(opsTask.getLabel())
                    .clazz(opsTask.getTaskType())
                    .config(opsTask.getLabel())
                    .data(result.getResult())
                    .status(result.getStatus())
                    .starttime(startTime)
                    .endtime(endTime)
                    .build();
            opsPlanTaskResultMapper.insert(taskResult);
            //插入执行结果
            planStatus = result.getStatus()
                    & planStatus;
            List<OpsTaskNextTask> nextTaskKeys
                    = opsTaskNextTaskMapper.selectList(
                    Wrappers.<OpsTaskNextTask>lambdaQuery()
                            .in(OpsTaskNextTask::getSourceTaskId, opsTask.getId()));
            for (OpsTaskNextTask caseKey : nextTaskKeys) {
                //入度减一
                inDegreeMap.put(caseKey.getTargetTaskId(),
                        null == inDegreeMap.get(caseKey.getTargetTaskId())
                                ? 0
                                : inDegreeMap.get(caseKey.getTargetTaskId()) - 1);
                //如果入度为0
                if (inDegreeMap.get(caseKey.getTargetTaskId()) == 0) {
                    s1.offer(caseKey.getTargetTaskId());
                }
            }
        }
        Timestamp endTime
                = new Timestamp(System.currentTimeMillis());
        opsPlanResult.setEndtime(endTime);
        opsPlanResult.setStatus(planStatus);
        opsPlanTaskMapper.updateById(opsPlanResult);
        return opsPlanResult;
    }


    private void initMap(List<OpsTask> taskList, Map<Long, Integer> inDegreeMap) {
        for (OpsTask task : taskList) {
            List<OpsTaskNextTask> nextTaskKeys
                    = opsTaskNextTaskMapper.selectList(
                    Wrappers.<OpsTaskNextTask>lambdaQuery()
                            .in(OpsTaskNextTask::getSourceTaskId, task.getId()));
            for (OpsTaskNextTask caseKey : nextTaskKeys) {
                inDegreeMap.put(caseKey.getSourceTaskId(),
                        null == inDegreeMap.get(caseKey.getSourceTaskId())
                                ? 1
                                : inDegreeMap.get(caseKey.getSourceTaskId()) + 1);
            }
        }
    }

    private TaskResult executeTask(OpsTask opsTask) {
        TaskResult result = new TaskResult();
        TestOpsManager testOpsManager = new TestOpsManager();
        //忽略大小写
        switch (opsTask.getTaskType().toLowerCase()) {
            case Constants.TESTFLOW: {
                OpsTestflowTaskConfig opsTestflowTaskConfig
                        = opsTestflowTaskConfigMapper.selectOne(
                        Wrappers.<OpsTestflowTaskConfig>lambdaQuery()
                                .eq(OpsTestflowTaskConfig::getOpsTaskId, opsTask.getId()));
                HashMap<String, String> map
                        = new HashMap<>();
                map.put(Constants.JOB_ID, String.valueOf(opsTestflowTaskConfig.getTaskflowTaskId()));
                result
                        = testOpsManager
                        .result(Constants.TESTFLOW, FastJsonUtil.toJson(map));
                testOpsManager
                        .execTask(Constants.TESTFLOW, FastJsonUtil.toJson(map));
                break;
            }
            case Constants.SONAR: {
                OpsSonarTaskConfig opsSonarTaskConfig
                        = opsSonarTaskConfigMapper.selectOne(
                        Wrappers.<OpsSonarTaskConfig>lambdaQuery()
                                .eq(OpsSonarTaskConfig::getOpsTaskId, opsTask.getId()));
                HashMap<String, String> map
                        = new HashMap<>();
                map.put(Constants.REPO_URL, opsSonarTaskConfig.getRepoUrl());
                result = testOpsManager
                        .execTask(Constants.SONAR, FastJsonUtil.toJson(map));
                break;
            }
            default: {}
        }
        return result;
    }
}

