package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/4/8 08:49
 */
public class FeatureResult {

        //feature_result_id
        private Long feature_result_id;
        //feature_id
        private Long feature_id;
        //parameter_value_index
        private Long parameter_value_index;
        //log_key
        private String log_key;
        //assertion_key
        private String assertion_key;
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

        public String getLog_key() {
                return log_key;
        }

        public void setLog_key(String log_key) {
                this.log_key = log_key;
        }

        public String getAssertion_key() {
                return assertion_key;
        }

        public void setAssertion_key(String assertion_key) {
                this.assertion_key = assertion_key;
        }

        public Timestamp getDatachanged_lasttime() {
                return datachanged_lasttime;
        }

        public void setDatachanged_lasttime(Timestamp datachanged_lasttime) {
                this.datachanged_lasttime = datachanged_lasttime;
        }
}
