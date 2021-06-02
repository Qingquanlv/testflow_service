package com.github.qingquanlv.testflow_service_api.entity.task.gettaskresults;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 20:47
 */
public class TaskStepResult {

        private Long stepId;

        private String logs;

        private String assertions;

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
