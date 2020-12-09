package com.github.qingquanlv.testflow_service_api.entity.cases;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2020/11/29 10:57
 * @Version 1.0
 */
public abstract class BaseCase {

        private CaseKey key;

        private List<CaseKey> nextKeys;

        public CaseKey getKey() {
                return key;
        }

        public void setKey(CaseKey key) {
                this.key = key;
        }

        public List<CaseKey> getNextKeys() {
                return nextKeys;
        }

        public void setNextKeys(List<CaseKey> nextKeys) {
                this.nextKeys = nextKeys;
        }
}
