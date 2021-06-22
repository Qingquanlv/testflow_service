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
                String.format("expValue:%s, atlValue:%s",
                        expObj, atlObj));
        if (null == expObj && null == atlObj) { }
        else if (null == expObj ||  null == atlObj) {
            errMsg = String.format("expected: \"%s\" not equals with actual: \"%s\".", expStr, atlStr);
        }
        else if (!expObj.equals(atlObj)) {
            errMsg = String.format("expected value: \"%s\" not equals with actual: \"%s\".", expStr, atlStr);
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
                String.format("expValue:%s, jsonFilter:%s, atlValue:%s",
                        expValue, JsonFilter, atlStr));
        List<Object> objList = ParamUtil.getMapValFromJson(atlStr, JsonFilter);
        if (objList == null || objList.isEmpty()) {
            throw new Exception(String.format("No matiched value for key \"%s\" Json string \"%s\" .", atlStr, JsonFilter));
        }
        if (!objList.get(0).toString().equals(expValue)) {
            errMsg =  String.format("expected value: \"%s\" is not equal with actual: \"%s\".", expValue, objList.get(0).toString());
        }
        return errMsg;
    }

    /**
     * 对比实体
     *
     */
    public String verify(String caseName, String expStr, String atlStr, String pkMapStr, String noCompareItemMapStr) throws Exception {
        String exp = BufferManager.getBufferByKey(expStr);
        String atl = BufferManager.getBufferByKey(atlStr);
        BufferManager.addConfigByKey(caseName,
                String.format("expValue:%s, atlValue:%s, primaryKey:%s, noCompareItem:%s" ,
                        exp, atl, pkMapStr, noCompareItemMapStr));
        //primary key map
        Map<String, List<String>> pkMap = ParamUtil.parseVerifyParam(BufferManager.getBufferByKey(pkMapStr));
        //no compare map
        Map<String, List<String>> noCompareItemMap = ParamUtil.parseVerifyParam(BufferManager.getBufferByKey(noCompareItemMapStr));
        VerifyUtil compareUtil = new VerifyUtil();
        return compareUtil.compareObj(exp, atl, pkMap, noCompareItemMap);
    }
}

