package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.CaseInfo;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.FeatureTaskResult;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.deletejob.DeleteJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.JobDetails;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Job;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.*;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.service.IScheduleJobService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Author Qingquan Lv
 * @Version 1.0
 */

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ScheduleJobImpl implements IScheduleJobService {

    private static Logger log = LoggerFactory.getLogger(TestFlowManager.class);

    @Autowired
    private JobConfigMapper jobConfigMapper;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private TaskResultMapper taskResultMapper;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private FeatureServiceImpl featureService;

    @Autowired
    ParameterDetailsMapper parameterDetailsMapper;

    @Autowired
    CazeMapper caseMapper;

    @Autowired
    FeatureMapper featureMapper;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    ParameterIndexStepResultMapper parameterIndexStepResultMapper;

    /**
     * 获取所有Job信息
     * @param request request
     * @return response
     */
    @Override
    public QueryAllJobResponse queryAllJob(QueryAllJobRequest request) {
        Status status = new Status();
        status.setSuccess(true);
        String filter
            = null == request.getFilter() ? "" : request.getFilter();
        long pageNum
            = null == request.getPageNum() ? 1L : request.getPageNum();
        long pageSize
            = null == request.getPageSize() ? 10L : request.getPageSize();
        //query job
        Page<Job> jobs
            = jobMapper.selectPage(
            new Page<Job>(pageNum, pageSize),
            Wrappers.<Job>lambdaQuery()
                .like(Job::getName, filter)
                .orderByDesc(Job::getId));
        //query all feature
        List<FeatureTab> features = featureMapper.selectList(Wrappers.emptyWrapper());
        //query all job config
        List<JobConfig> jobConfigs = jobConfigMapper.selectList(Wrappers.emptyWrapper());
        List<JobDetails> jobDetailsList = new ArrayList<>();
        for (Job item : jobs.getRecords()) {
            FeatureTab feature = features.stream()
                .filter(i->item.getFeatureId().equals(i.getFeature_id()))
                .findFirst().orElse(null);
            JobConfig jobConfig = jobConfigs.stream()
                .filter(i->item.getId().equals(i.getJobId()))
                .findFirst().orElse(null);
            JobDetails jobDetails
                = JobDetails.builder()
                .id(item.getId())
                .name(item.getName())
                .featureId(item.getFeatureId())
                .featureName(null == feature ? "" : feature.getFeature_name())
                .paramName(item.getParam_name())
                .paramIndex(item.getParamIndexId())
                .cron(null == jobConfig ? "" : jobConfig.getCron())
                .status(null == jobConfig ? 0 : jobConfig.getStatus())
                .dataChangedLastTime(item.getDatachangedLasttime())
                .description(null == jobConfig ? "" : jobConfig.getDescription()
                ).build();
            jobDetailsList.add(jobDetails);
        }
        return QueryAllJobResponse.builder()
            .jobDetails(jobDetailsList)
            .total(jobs.getTotal())
            .status(status)
            .build();
    }


    /**
     * 创建Job
     *
     * @param request request
     * @return response
     */
    @Override
    public CreateJobResponse createJob(CreateJobRequest request) {
        Status status = new Status();
        status.setSuccess(true);

        CreateJobResponse createJobResponse = new CreateJobResponse();
        Job job
                = Job.builder()
                .name(request.getName())
                .featureId(request.getFeatureId())
                .param_name(request.getParamName())
                .paramIndexId(request.getParamIndex())
                .build();
        jobMapper.insert(job);
        JobConfig jobConfig
                = JobConfig.builder()
                .jobId(job.getId())
                //默认执行类
                .className("com.github.qingquanlv.testflow_service_api.job.MyJob")
                .description(request.getDescription())
                .cron(request.getCron())
                //新创建Job status为0
                .status(0)
                .build();
        jobConfigMapper.insert(jobConfig);
        createJobResponse.setStatus(status);
        return createJobResponse;
    }

    /**
     * 删除Job
     *
     * @param jobId
     * @return
     */
    @Override
    public DeleteJobResponse deleteJob(Long jobId) {
        Status status = new Status();
        status.setSuccess(true);
        DeleteJobResponse createJobResponse = new DeleteJobResponse();
        try {
            unloadJob(String.valueOf(jobId));
        }
        catch (Exception ex) {
            status.setSuccess(false);
            status.setMessage("Unload Job Failed");
        }
        jobMapper.deleteById(jobId);
        jobConfigMapper.delete(
                Wrappers.<JobConfig>lambdaQuery()
                        .eq(JobConfig::getJobId, jobId));
        createJobResponse.setStatus(status);
        return createJobResponse;
    }

    /**
     * 通过JobId更新Job
     *
     * @param request
     * @return
     */
    @Override
    public UpdateJobResponse updateJob(UpdateJobRequest request) {
        Status status = new Status();
        status.setSuccess(true);
        UpdateJobResponse updateJobResponse = new UpdateJobResponse();
        Job job
                = Job.builder()
                .id(request.getJobId())
                .name(request.getName())
                .featureId(request.getFeatureId())
                .param_name(request.getParamName())
                .paramIndexId(request.getParamIndex())
                .build();
        JobConfig jobConfig
                = JobConfig.builder()
                .jobId(job.getId())
                .cron(request.getCron())
                //默认执行类
                .className("com.github.qingquanlv.testflow_service_api.job.MyJob")
                .description(request.getDescription())
                .build();
        jobMapper.updateById(job);
        jobConfigMapper.update(
                jobConfig,
                Wrappers.<JobConfig>lambdaQuery()
                        .eq(JobConfig::getJobId, job.getId()));
        updateJobResponse.setStatus(status);
        return updateJobResponse;
    }

    /**
     * 更新任务Status
     *
     * @param request
     * @throws SchedulerException
     */
    @Override
    public SetStatusResponse updateStatus(SetStatusRequest request) {
        Status status = new Status();
        status.setSuccess(true);
        JobConfig taskConfigEntity = jobConfigMapper.selectOne(
                Wrappers.<JobConfig>lambdaQuery()
                        .eq(JobConfig::getJobId, request.getJobId()));
        //不存在job返回error
        if (taskConfigEntity == null){
            status.setSuccess(false);
            status.setMessage("cannot find job config");
        }
        //更新status为开启状态
        else if (1 == request.getStatus()) {
            //update job status
            taskConfigEntity.setStatus(1);
            jobConfigMapper.update(
                    taskConfigEntity,
                    Wrappers.<JobConfig>lambdaQuery()
                            .eq(JobConfig::getJobId, request.getJobId()));
            try {
                JobDetail jobDetail = getJobDetail(taskConfigEntity);
                CronTrigger cronTrigger = getCronTrigger(taskConfigEntity);
                scheduler.scheduleJob(jobDetail, cronTrigger);
            } catch (Exception e) {
                log.error("加载定时任务异常",e);
                status.setSuccess(false);
                status.setMessage(String.format("%s %s", "load scheduler failed", e));
            }
        }
        //更新status为关闭状态
        else {
            try {
                //update job status
                taskConfigEntity.setStatus(0);
                jobConfigMapper.update(
                        taskConfigEntity,
                        Wrappers.<JobConfig>lambdaQuery()
                                .eq(JobConfig::getJobId, request.getJobId()));
                // 停止触发器
                scheduler.pauseTrigger(TriggerKey.triggerKey(String.valueOf(request.getJobId())));
                // 卸载定时任务
                scheduler.unscheduleJob(TriggerKey.triggerKey(String.valueOf(request.getJobId())));
                // 删除原来的job
                scheduler.deleteJob(JobKey.jobKey(String.valueOf(request.getJobId())));
            } catch (Exception e) {
                log.error("卸载定时任务异常",e);
                status.setSuccess(false);
                status.setMessage(String.format("%s %s", "unload scheduler failed", e));
            }
        }
        SetStatusResponse rsp
                = SetStatusResponse.builder()
                .status(status).build();
        return rsp;
    }

    @Override
    public List<Task> pendingJob(List<Long> jobIds) {
        List<Task> tasks = new ArrayList<>();
        List<Job> jobs = jobMapper.selectList(
                Wrappers.<Job>lambdaQuery()
                        .in(Job::getId, jobIds));
        for (Long jobId : jobIds) {
            Job job
                    = jobs.stream()
                    .filter(item->item.getId()==jobId)
                    .findFirst().orElse(null);
            Task task
                    = Task.builder()
                    .jobId(jobId)
                    .status(4)
                    .parameterName(job.getParam_name())
                    .featureId(job.getFeatureId())
                    .parameterValueIndex(job.getParamIndexId())
                    .build();
            taskMapper.insert(task);
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * 手动异步执行Job
     *
     * @param task
     */
    @Async("taskExecutor")
    @Override
    public void execJob(Task task) {
        Integer taskStatus = 1;
        //ParamIndex 格式转为 list<Long>
        List<Long> index
                = Utils.toListLong(task.getParameterValueIndex());
        List<Caze> cazes
                = caseMapper.selectList(
                Wrappers.<Caze>lambdaQuery()
                        .eq(Caze::getFeatureId,
                                task.getFeatureId()));
        //过滤获取Parameter中存在的Index
        List<com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab> parameters
                = CollectionUtils.isEmpty(index) ?
                parameterDetailsMapper.selectList(
                        Wrappers.<com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab>lambdaQuery()
                                .eq(com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab::getParameterName, task.getParameterName())) :
                parameterDetailsMapper.selectList(
                        Wrappers.<com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab>lambdaQuery()
                                .eq(com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab::getParameterName, task.getParameterName())
                                .in(com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab::getParameterValueIndex, index));
        Set<Long> parameterIndex
                = parameters.stream()
                .map(com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab::getParameterValueIndex)
                .collect(Collectors.toSet());
        //根据Index遍历执行Case
        Timestamp taskStartTime = new Timestamp(System.currentTimeMillis() + 60 * 60 * 8 * 1000);
        //update task
        if (CollectionUtils.isEmpty(parameterIndex)) {
            task.setStatus(0);
        }
        else {
            task.setStatus(2);
        }
        task.setStarttime(taskStartTime);
        taskMapper.updateById(task);
        for (Long i : parameterIndex) {
            //构建参数
            List<Parameter> parameterList = new ArrayList<>();
            Map<String, String> pMap = new HashMap<>();
            List<com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab> plist =
                    parameters
                            .stream()
                            .filter(item->i.equals(item.getParameterValueIndex()))
                            .collect(Collectors.toList());
            for (com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab item : plist) {
                Parameter parameter = new Parameter();
                parameter.setParameter_value(item.getParameterValue());
                parameter.setParameter_key(item.getParameterKey());
                pMap.put(item.getParameterKey(), item.getParameterValue());
                parameterList.add(parameter);
            }
            Timestamp startTime = new Timestamp(System.currentTimeMillis() + 60 * 60 * 8 * 1000);
            FeatureTaskResult taskResult;
            try {
                //执行feature
                taskResult
                        = featureService.execFeature(
                        parameterList, cazes);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                taskResult
                        = FeatureTaskResult.builder()
                        .status(3).build();
            }
            Timestamp endTime
                    = new Timestamp(System.currentTimeMillis() + 60 * 60 * 8 * 1000);
            taskStatus
                    = taskStatus
                    & (null == taskResult.getStatus() ? 0
                    : taskResult.getStatus());
            TaskResult result = TaskResult.builder()
                    .taskId(task.getId())
                    .parameter_name(task.getParameterName())
                    .parameter_value_index(i)
                    .parameter_value(FastJsonUtil.toJson(pMap))
                    .status(taskResult.getStatus())
                    .starttime(startTime)
                    .endtime(endTime).build();
            taskResultMapper.insert(result);
            List<CaseInfo> caseInfos
                    = FastJsonUtil.toListBean(
                    taskResult.getLogs(), CaseInfo.class);
            List<ParameterIndexStepResult> caseStepInfos
                    = setCaseStepResult(result.getId(), caseInfos);
            parameterIndexStepResultMapper.saveBatchByNative(caseStepInfos);

        }
        Timestamp taskEndTime = new Timestamp(System.currentTimeMillis() + 60 * 60 * 8 * 1000);
        task.setEndtime(taskEndTime);
        task.setStatus(taskStatus);
        taskMapper.update(
                task,
                Wrappers.<Task>lambdaQuery()
                        .eq(Task::getId, task.getId()));
    }

    /**
     * 加载所有status为1的定时任务
     */
    @Override
    public void startJob(){
        List<JobConfig> taskConfigEntities = jobConfigMapper.selectList(
                Wrappers.<JobConfig>lambdaQuery()
                        .eq(JobConfig::getStatus, 1));
        if (taskConfigEntities == null || taskConfigEntities.size() == 0){
            log.error("定时任务加载数据为空");
            return;
        }
        for (JobConfig configEntity : taskConfigEntities) {
            CronTrigger cronTrigger = null;
            JobDetail jobDetail = null;
            try {
                cronTrigger = getCronTrigger(configEntity);
                jobDetail = getJobDetail(configEntity);
                scheduler.scheduleJob(jobDetail,cronTrigger);
                log.info("编号：{}定时任务加载成功",configEntity.getJobId());
            }catch (Exception e){
                log.error("编号：{}定时任务加载失败",configEntity.getJobId());
            }

        }
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("定时任务启动失败",e);
        }
    }

    /**
     * 停止任务
     * @param taskId
     */
    @Override
    public void stopJob(String taskId) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(taskId));
    }

    /**
     * 恢复任务
     * @param taskId
     * @throws SchedulerException
     */
    @Override
    public void resumeJob(String taskId) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(taskId));
    }

    /**
     * 添加新的job
     * @param jobId
     * @throws SchedulerConfigException
     */
    @Override
    public void loadJob(String jobId) throws SchedulerConfigException {
        JobConfig taskConfigEntity = jobConfigMapper.selectOne(
                Wrappers.<JobConfig>lambdaQuery()
                        .eq(JobConfig::getJobId, jobId)
                        .eq(JobConfig::getStatus, 1));
        if (taskConfigEntity == null){
            throw new SchedulerConfigException("未找到相关Job配置");
        }
        try {
            JobDetail jobDetail = getJobDetail(taskConfigEntity);
            CronTrigger cronTrigger = getCronTrigger(taskConfigEntity);
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (Exception e) {
            log.error("加载定时任务异常",e);
            throw new SchedulerConfigException("加载定时任务异常", e);
        }
    }

    @Override
    public void unloadJob(String jobId) throws SchedulerException {
        // 停止触发器
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobId));
        // 卸载定时任务
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobId));
        // 删除原来的job
        scheduler.deleteJob(JobKey.jobKey(jobId));
    }

    /**
     * 重新加载执行计划
     * @throws Exception
     */
    @Override
    public void reload(String jobId) throws Exception {
        JobConfig taskConfigEntity = jobConfigMapper.selectOne(
                Wrappers.<JobConfig>lambdaQuery()
                        .eq(JobConfig::getJobId, jobId)
                        .eq(JobConfig::getStatus, 1));

        Long jobCode = taskConfigEntity.getJobId();
        // 获取以前的触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(String.valueOf(jobCode));
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 删除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除原来的job
        scheduler.deleteJob(JobKey.jobKey(String.valueOf(jobCode)));
        JobDetail jobDetail = getJobDetail(taskConfigEntity);
        CronTrigger cronTrigger = getCronTrigger(taskConfigEntity);
        // 重新加载job
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    //组装JobDetail
    private JobDetail getJobDetail(JobConfig configEntity) throws ClassNotFoundException {

        Class<? extends org.quartz.Job> aClass = Class.forName(configEntity.getClassName()).asSubclass(org.quartz.Job.class);

        return JobBuilder.newJob()
                .withIdentity(JobKey.jobKey(String.valueOf(configEntity.getJobId())))
                .withDescription(configEntity.getDescription())
                .ofType(aClass).build();
    }

    //组装CronTrigger
    private CronTrigger getCronTrigger(JobConfig configEntity){
        CronTrigger cronTrigger = null;
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(configEntity.getCron());
        cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(String.valueOf(configEntity.getJobId())))
                .withSchedule(cronScheduleBuilder)
                .build();
        return cronTrigger;
    }

    // 组装test log
    private List<ParameterIndexStepResult> setCaseStepResult(Integer taskResultId, List<CaseInfo> caseInfos) {
        int i = 0;
        List<ParameterIndexStepResult> results = new ArrayList<>();
        for (CaseInfo caseInfo : caseInfos) {
            ParameterIndexStepResult result =
                    ParameterIndexStepResult.builder()
                            .taskResultId(taskResultId)
                            .resultIndexId(i)
                            .clazz(caseInfo.getClazz())
                            .config(caseInfo.getConfig())
                            .label(caseInfo.getLabel())
                            .data(caseInfo.getData())
                            .status(caseInfo.getStatus()).build();
            results.add(result);
            i++;
        }
        return results;
    }
}
