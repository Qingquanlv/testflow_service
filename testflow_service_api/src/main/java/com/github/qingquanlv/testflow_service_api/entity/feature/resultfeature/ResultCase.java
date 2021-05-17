package com.github.qingquanlv.testflow_service_api.entity.feature.resultfeature;

/**
 * @Author: qingquan.lv
 * @Date: 2021/4/14 20:52
 */
public class ResultCase {

        /**
         * index
         */
        private Integer index;
        /**
         * info
         */
        private String info;
        /**
         * assertion
         */
        private String assertion;


        public Integer getIndex() {
                return index;
        }

        public void setIndex(Integer index) {
                this.index = index;
        }

        public String getInfo() {
                return info;
        }

        public void setInfo(String info) {
                this.info = info;
        }

        public String getAssertion() {
                return assertion;
        }

        public void setAssertion(String assertion) {
                this.assertion = assertion;
        }
}
