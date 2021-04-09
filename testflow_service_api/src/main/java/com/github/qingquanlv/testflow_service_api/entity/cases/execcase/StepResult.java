package com.github.qingquanlv.testflow_service_api.entity.cases.execcase;

import com.github.qingquanlv.testflow_service_api.entity.cases.CaseKey;

/**
 * @Author: qingquan.lv
 * @Date: 2021/3/12 16:50
 */
public class StepResult {

        private CaseKey caseKey;

        private String info;

        private String asseration;

        public CaseKey getCaseKey() {
                return caseKey;
        }

        public void setCaseKey(CaseKey caseKey) {
                this.caseKey = caseKey;
        }

        public String getInfo() {
                return info;
        }

        public void setInfo(String info) {
                this.info = info;
        }

        public String getAsseration() {
                return asseration;
        }

        public void setAsseration(String asseration) {
                this.asseration = asseration;
        }
}
