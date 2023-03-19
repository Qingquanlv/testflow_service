package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
public class FeatureResult {

        /**
         * feature_result_id
         */
        private Long feature_result_id;
        //feature_id
        private Long feature_id;
        //task_id
        private Long task_id;
        //parameter_value_index
        private Long parameter_value_index;
        //log
        private String logs;
        //assertion_key
        private String assertions;
        //time_stamp
        private Timestamp datachanged_lasttime;

        public Long getFeature_result_id() {
                return feature_result_id;
        }

        public void setFeature_result_id(Long feature_result_id) {
                this.feature_result_id = feature_result_id;
        }

        public Long getFeature_id() {
                return feature_id;
        }

        public void setFeature_id(Long feature_id) {
                this.feature_id = feature_id;
        }

        public Long getParameter_value_index() {
                return parameter_value_index;
        }

        public void setParameter_value_index(Long parameter_value_index) {
                this.parameter_value_index = parameter_value_index;
        }

        public Timestamp getDatachanged_lasttime() {
                return datachanged_lasttime;
        }

        public void setDatachanged_lasttime(Timestamp datachanged_lasttime) {
                this.datachanged_lasttime = datachanged_lasttime;
        }

        public Long getTask_id() {
                return task_id;
        }

        public void setTask_id(Long task_id) {
                this.task_id = task_id;
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
