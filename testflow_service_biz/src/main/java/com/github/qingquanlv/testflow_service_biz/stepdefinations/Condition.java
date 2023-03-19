package com.github.qingquanlv.testflow_service_biz.stepdefinations;

import com.github.qingquanlv.testflow_service_biz.common.BufferManager;
import com.github.qingquanlv.testflow_service_biz.utilities.ParamUtil;
import com.github.qingquanlv.testflow_service_biz.utilities.VerifyUtil;

/**
 * @Author: qingquan.lv
 */
public class Condition {

        /**
         * IF
         *
         */
        public boolean ifCondition(String caseName, String path, String operator, String atlStr) throws Exception {
                String expStr = null == path ? "" : ParamUtil.parseParamPath(path);
                BufferManager.addConfigByKey(caseName,
                        String.format("expect value:%s, actual value:%s, operator value:%s.", expStr, atlStr, operator));
                VerifyUtil compareUtil = new VerifyUtil();
                return compareUtil.compareValue(operator, expStr, atlStr);
        }

        /**
         * FOR
         *
         */
        public boolean forCondition(String caseName, String path, String operator, String atlStr, Long wait_time) throws Exception {
                String expStr = null == path ? "" : ParamUtil.parseParamPath(path);
                BufferManager.addConfigByKey(caseName,
                        String.format("expect value:%s, actual value:%s, operator value:%s.", expStr, atlStr, operator));
                VerifyUtil compareUtil = new VerifyUtil();
                try {
                        //1000 毫秒，也就是1秒.
                        Thread.sleep(wait_time);
                } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                }
                return compareUtil.compareValue(operator, expStr, atlStr);
        }
}
