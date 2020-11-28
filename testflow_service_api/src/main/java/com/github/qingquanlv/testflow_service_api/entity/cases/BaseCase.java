package com.github.qingquanlv.testflow_service_api.entity.cases;

import java.util.List;

public abstract class BaseCase {

        private String key;
        private List<String> nextKeys;


        public String getKey() {
                return key;
        }

        public void setKey(String key) {
                this.key = key;
        }

        public List<String> getNextKeys() {
                return nextKeys;
        }

        public void setNextKeys(List<String> nextKeys) {
                this.nextKeys = nextKeys;
        }
}
