package com.github.qingquanlv.testflow_service_biz.stepdefinations;

import com.github.qingquanlv.testflow_service_biz.common.BufferManager;
import com.github.qingquanlv.testflow_service_biz.utilities.ParamUtil;
import com.github.qingquanlv.testflow_service_biz.utilities.VerifyUtil;

import java.util.List;
import java.util.Map;

public class Verify {

    /**
     * 对比字符串
     *
     */
    public String verify(String caseName, String expStr, String atlStr) throws Exception {
        String errMsg = "";
        String expObj = BufferManager.getBufferByKey(expStr);
        String atlObj = BufferManager.getBufferByKey(atlStr);
        BufferManager.addConfigByKey(caseName,
                String.format("expKey:%s，expValue:%s, atlKey:%s, atlValue:%s",
                        expStr, expObj, atlStr, atlObj));
        if (null == expObj && null == atlObj) { }
        else if (null == expObj ||  null == atlObj) {
            errMsg = String.format("expected: \"%s\" not equals with actual: \"%s\".", expObj, atlObj);
        }
        else if (!expObj.equals(atlObj)) {
            errMsg = String.format("expected value: \"%s\" not equals with actual: \"%s\".", expObj, atlObj);
        }
        return errMsg;
    }

    /**
     * 对比Json某一节点
     *
     */
    public String verify(String caseName, String atlObj, String JsonFilter, String expValue) throws Exception {
        String errMsg = "";
        String atlStr = BufferManager.getBufferByKey(atlObj);
        BufferManager.addConfigByKey(caseName,
                String.format("expValue:%s, jsonFilter:%s, atlKey:%s, atlObject:%s",
                        expValue, JsonFilter, atlObj, atlStr));
        String str = ParamUtil.getMapValFromJson(atlStr, JsonFilter);
        if (str == null || str.isEmpty()) {
            throw new Exception(String.format("No matched value for key \"%s\" Json string \"%s\" .", atlStr, JsonFilter));
        }
        if (!str.equals(expValue)) {
            errMsg =  String.format("expected value: \"%s\" is not equal with actual: \"%s\".", expValue, str);
        }
        return errMsg;
    }

    /**
     * 对比实体
     *
     */
    public String verify(String caseName, String expStr, String atlStr, String pkMapStr, String noCompareItemMapStr, String thresholdStr) throws Exception {
        String exp = BufferManager.getBufferByKey(expStr);
        String atl = BufferManager.getBufferByKey(atlStr);
        BufferManager.addConfigByKey(caseName,
                String.format("{\"expKey\":\"%s\", \"expValue\":%s, \"atlKey\":\"%s\", \"atlValue\":%s, \"primaryKey\":\"%s\", \"noCompareItem\":\"%s\", \"threshold\":\"%s\"}" ,
                        expStr, exp, atlStr, atl, pkMapStr, noCompareItemMapStr, thresholdStr));
        //primary key map
        Map<String, List<String>> pkMap = ParamUtil.parseVerifyParam(pkMapStr);
        //no compare map
        Map<String, List<String>> noCompareItemMap = ParamUtil.parseVerifyParam(noCompareItemMapStr);
        //threshold map
        Map<String, List<String>> thresholdMap = ParamUtil.parseVerifyParam(thresholdStr);
        VerifyUtil compareUtil = new VerifyUtil();
        return compareUtil.compareObj(exp, atl, pkMap, noCompareItemMap, thresholdMap);
    }
}

