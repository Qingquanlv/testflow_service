package com.github.qingquanlv.testops_service_biz.entity;

/**
 * @Author: qingquan.lv
 * @Date: 2022/1/20 下午8:05
 */
public class TaskResult {

        private String result;

        private Integer status;

        public String getResult() {
                return result;
        }

        public void setResult(String result) {
                this.result = result;
        }


        public Integer getStatus() {
                return status;
        }

        public void setStatus(Integer status) {
                this.status = status;
        }
}
