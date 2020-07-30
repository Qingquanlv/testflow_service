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
    public String verify(String expStr, String atlStr) throws Exception {
        String errMsg = "";
        String expObj = BufferManager.getBufferByKey(expStr);
        String atlObj = BufferManager.getBufferByKey(atlStr);
        if (!expObj.equals(atlObj)) {
            errMsg = String.format("\n" + "expected: \"%s\" not equals with actual: \"%s\".\n", expStr, atlStr);
        }
        return errMsg;
    }

    /**
     * 对比Json某一节点
     *
     */
    public String verify(String atlObj, String JsonFilter, String expValue) throws Exception {
        String errMsg = "";
        String atlStr = BufferManager.getBufferByKey(atlObj);

        List<Object> objList = ParamUtil.getMapValFromJson(atlStr, JsonFilter);
        if (objList == null || objList.isEmpty()) {
            throw new Exception(String.format("No matiched value for key \"%s\" Json string \"%s\" .", atlStr, JsonFilter));
        }
        if (!objList.get(0).toString().equals(expValue)) {
            errMsg =  String.format("\n" + "expected: \"%s\" is not equal with actual: \"%s\".", expValue, objList.get(0).toString());
        }
        return errMsg;
    }

    /**
     * 对比实体
     *
     */
    public String verify(String paramType, String expStr, String atlStr, String pkMapStr, String noCompareItemMapStr) throws Exception {
        Object expObj = ParamUtil.getParameType(expStr, paramType);
        Object atlObj = ParamUtil.getParameType(atlStr, paramType);
        //primary key map
        Map<String, List<String>> pkMap = ParamUtil.parseVerifyParam(pkMapStr);
        //no compare map
        Map<String, List<String>> noCompareItemMap = ParamUtil.parseVerifyParam(noCompareItemMapStr);
        VerifyUtil compareUtil = new VerifyUtil();
        compareUtil.compareEntity(expObj, atlObj, pkMap, noCompareItemMap);
        return compareUtil.getErrorMsg();
    }
}

