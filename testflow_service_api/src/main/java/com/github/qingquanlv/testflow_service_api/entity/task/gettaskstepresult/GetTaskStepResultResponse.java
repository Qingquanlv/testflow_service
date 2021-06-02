package com.github.qingquanlv.testflow_service_api.entity.task.gettaskstepresult;

import com.github.qingquanlv.testflow_service_api.entity.Status;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 19:56
 */
public class GetTaskStepResultResponse {

        //status
        private Status status;

        private Long featureId;

        private String featureName;

        private Long taskId;

        private String taskName;

        private Long stepId;

        private String logs;

        private String assertions;

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

        public Long getStepId() {
                return stepId;
        }

        public void setStepId(Long stepId) {
                this.stepId = stepId;
        }

        public String getLogs() {
                return logs;
        }

        public void setLogs(String logs) {
                this.logs = logs;
        }

        public String getAssertions() {
                return assertions;
        }

        public void setAssertions(String assertions) {
                this.assertions = assertions;
        }
}
