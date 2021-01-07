package com.github.qingquanlv.testflow_service_biz;

import com.github.qingquanlv.testflow_service_biz.common.BufferManager;
import com.github.qingquanlv.testflow_service_biz.common.LogHelper;
import com.github.qingquanlv.testflow_service_biz.stepdefinations.*;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class TestFlowManager {


    /**
     * 私有化构造方法
     */
    public TestFlowManager(){

        BufferManager.initBufferMap();
        LogHelper.initLog();
    }

    /**
     * 销毁缓存
     *
     */
    public TestFlowManager deposed(){
        BufferManager.deposeBufferMap();
        LogHelper.deposeLog();
        return this;
    }

    /**
     * 发送请求
     * @param requestId : 请求报文Id
     * @param requestStr : 请求报文
     * @param contentType : 保存response的key
     * @param config : 请求配置
     * @param headerMap : 请求头
     * @param requestType : 请求报文种类 post/get
     * @param contentType : 请求报文格式 xml/json
     * @param url : 请求url
     *
     */
    public String sendRequest(String requestId, String requestStr, HashMap<String, String> config, HashMap<String, String> headerMap, String requestType, String contentType, String url) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendRequest", requestStr, url, requestId);
        responceStr = request.sendRequest(requestStr, config, headerMap, requestType, contentType, url);
        //BufferManager.addBufferByKey(requestId, responceStr);
        LogHelper.stepAfterLog(requestId, responceStr);
        return responceStr;
    }

    /**
     *  使用String格式字符串
     *
     * @param convertMethodSource
     * @param convertMethodName
     * @param returnType
     * @param paramList
     * @return
     */
    public String sourceParse(String convertMethodSource, String convertMethodName, String returnType, List<String> paramList) {
        Parser parser = new Parser();
        String str = "";
        LogHelper.stepExecLog("sourceParse", convertMethodSource, convertMethodName, returnType, paramList.toString());
        try {
            //覆盖一个参数到四个参数的场景
            if (2 == paramList.size()) {
                str = parser.parseValueVidStr(convertMethodSource, convertMethodName, returnType, paramList.get(0), paramList.get(1));
            }
            if (2 == paramList.size()) {
                str = parser.parseValueVidStr(convertMethodSource, convertMethodName, returnType, paramList.get(0), paramList.get(1), paramList.get(2), paramList.get(3));
            }
            if (3 == paramList.size()) {
                str = parser.parseValueVidStr(convertMethodSource, convertMethodName, returnType, paramList.get(0), paramList.get(1), paramList.get(2), paramList.get(3), paramList.get(4), paramList.get(5));
            }
            if (4 == paramList.size()) {
                str = parser.parseValueVidStr(convertMethodSource, convertMethodName, returnType, paramList.get(0), paramList.get(1), paramList.get(2), paramList.get(3), paramList.get(4), paramList.get(5), paramList.get(6), paramList.get(7));
            }
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Init object failed"));
        }
        return str;
    }


    /**
     * 添加缓存
     *
     * @param bufferKey
     * @param bufferVal
     * @return
     */
    public TestFlowManager addBuffer(String bufferKey, String bufferVal) {
        Buffer buffer = new Buffer();
        LogHelper.stepExecLog("addBuffer", bufferKey, bufferVal);
        try {
            buffer.addStepBuffer(bufferKey, bufferVal);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("add Buffer key \"%s\" value \"%s\" failed: %s", bufferKey, bufferVal, ex));
        }
        return this;
    }

    /**
     * 获取缓存中的数据
     *
     * @param bufferKey
     * @return
     */
    public TestFlowManager getBuffer(String bufferKey) {
        Buffer buffer = new Buffer();
        LogHelper.stepExecLog("addBuffer", bufferKey);
        try {
            buffer.queryStepBuffer(bufferKey);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("add Buffer key \"%s\" value \"%s\" failed: %s", bufferKey));
        }
        return this;
    }

    /**
     * 对比缓存中的Json
     * @param expObj : 预期值缓存Key
     * @param atlObj : 实际值缓存Key
     *
     */
    public String verify(String expObj, String atlObj) {
        Verify verify = new Verify();
        String errorMsg = "";
        LogHelper.stepExecLog("verify", expObj, atlObj);
        try {
            errorMsg = verify.verify(expObj, expObj);
            if (!"".equals(errorMsg)) {
                deposed();
                throw new AssertionError(String.format("\n" + errorMsg));
            }
        }
        catch (Exception ex) {
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return errorMsg;
    }

    /**
     * 对比两个相同类型的实体
     * @param paramType : 实体类型
     * @param expObj : 预期结果值缓存Key
     * @param expObj : 实际结果值缓存Key
     * @param expObj : 对比实体List主键
     * @param atlObj : 实体中不对比的字段
     *
     */
    public String verify(String paramType, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr) {
        Verify verify = new Verify();
        String errorMsg = "";
        LogHelper.stepExecLog("verify", paramType, expObj, atlObj, pkMapStr, noCompareItemMapStr);
        try {
            errorMsg = verify.verify(paramType, expObj, atlObj, pkMapStr, noCompareItemMapStr);
            if (!"".equals(errorMsg)) {
                deposed();
                throw new AssertionError(String.format("\n" + errorMsg));
            }
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return errorMsg;
    }

    /**
     * 检验缓存中的Json某个key值
     * @param atlObj : 实际结果值缓存Key
     * @param JsonFilter : 定位Json的xpath
     * @param expValue : 特定key的预期值
     *
     */
    public String verify(String atlObj, String JsonFilter, String expValue) {
        Verify verify = new Verify();
        String errorMsg = "";
        LogHelper.stepExecLog("verify", atlObj, JsonFilter, expValue);
        try {
            errorMsg = verify.verify(atlObj, JsonFilter, expValue);
            if (!"".equals(errorMsg)) {
                deposed();
                throw new AssertionError(String.format("\n" + errorMsg));
            }
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return errorMsg;
    }

    /**
     * 通过原生SQL语句查询DB
     *
     * @param sql sql map 参数
     * @return
     */
    public String queryDataBase(String sql) {
        Database database= new Database();
        String str;
        //LogHelper.stepExecLog("queryDataBase", bufferKey, sql);
        try {
            str = database.queryDataBase(sql);
            //BufferManager.addBufferByKey(bufferKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Query datebase failed: " + ex));
        }
        //LogHelper.stepAfterLog(bufferKey, str);
        return str;
    }
}

