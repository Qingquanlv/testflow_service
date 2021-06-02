package com.github.qingquanlv.testflow_service_api.entity.task.gettaskresults;

import com.github.qingquanlv.testflow_service_api.entity.Status;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 19:55
 */
public class GetTaskResultResponse {

        //status
        private Status status;

        private Long featureId;

        private String featureName;

        private Long taskId;

        private String taskName;

        private List<TaskStepResult> taskStepResults;

        public Status getStatus() {
                return status;
        }

        public void setStatus(Status status) {
                this.status = status;
        }

        public Long getFeatureId() {
                return featureId;
        }

        public void setFeatureId(Long featureId) {
                this.featureId = featureId;
        }

        public String getFeatureName() {
                return featureName;
        }

        public void setFeatureName(String featureName) {
                this.featureName = featureName;
        }

        public Long getTaskId() {
                return taskId;
        }

        public void setTaskId(Long taskId) {
                this.taskId = taskId;
        }

        public String getTaskName() {
                return taskName;
        }

        public void setTaskName(String taskName) {
                this.taskName = taskName;
        }

        public List<TaskStepResult> getTaskStepResults() {
                return taskStepResults;
        }

        public void setTaskStepResults(List<TaskStepResult> taskStepResults) {
                this.taskStepResults = taskStepResults;
        }
}
