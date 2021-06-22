package com.github.qingquanlv.testflow_service_api.entity.cases_v2;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/22 20:10
 */
public class CaseInfo {

        private String label;
        private String clazz;
        private String config;
        private String data;

        public String getLabel() {
                return label;
        }

        public void setLabel(String label) {
                this.label = label;
        }

        public String getClazz() {
                return clazz;
        }

        public void setClazz(String clazz) {
                this.clazz = clazz;
        }

        public String getConfig() {
                return config;
        }

        public void setConfig(String config) {
                this.config = config;
        }

        public String getData() {
                return data;
        }

        public void setData(String data) {
                this.data = data;
        }
}
