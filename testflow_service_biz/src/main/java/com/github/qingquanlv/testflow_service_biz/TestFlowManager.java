package com.github.qingquanlv.testflow_service_biz;

import com.github.qingquanlv.testflow_service_biz.common.AssertionHelper;
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

    private String pattern;

    /**
     * 私有化构造方法
     */
    public TestFlowManager(){
        BufferManager.initBufferMap();
        LogHelper.initLog();
    }

    /**
     * 私有化构造方法
     */
    public TestFlowManager(String pattern){
        this.pattern = String.format("%s:", pattern);
        BufferManager.initBufferMap(pattern);
        LogHelper.initLog();
    }

    /**
     * 销毁缓存
     *
     */
    public void deposed(){
        BufferManager.deposeBufferMap(pattern);
        LogHelper.deposeLog();
    }

    /**
     * 发送请求
     * @param caseName : 请求caseName
     * @param requestStr : 请求报文
     * @param contentType : 保存response的key
     * @param config : 请求配置
     * @param headerMap : 请求头
     * @param requestType : 请求报文种类 post/get
     * @param contentType : 请求报文格式 xml/json
     * @param url : 请求url
     *
     */
    public String sendRequest(String caseName, String requestStr, HashMap<String, String> config, HashMap<String, String> headerMap, String requestType, String contentType, String url) {
        Request request = new Request();
        String responseStr;
        try {
            LogHelper.stepExecLog("sendRequest", requestStr, url, caseName);
            responseStr = request.sendRequest(caseName, requestStr, config, headerMap, requestType, contentType, url);
        }
        catch (Exception ex) {
            throw new AssertionError(String.format("Send request failed: %s", ex));
        }
        LogHelper.stepAfterLog(caseName, responseStr);
        return responseStr;
    }

    /**
     *  使用String格式字符串
     *
     * @param convertMethodSource
     * @param paramList
     * @return
     */
    public String sourceParse(String caseName, String convertMethodSource,  List<String> paramList) {
        Parser parser = new Parser();
        String str = "";
        try {
            LogHelper.stepExecLog("sourceParse", convertMethodSource, paramList.toString());
            //覆盖一个参数到四个参数的场景
            str = parser.parseValueVidStr(caseName, convertMethodSource, paramList);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Parse object failed"));
        }
        LogHelper.stepAfterLog(caseName, str);
        return str;
    }

    /**
     * 通过原生SQL语句查询DB
     *
     * @param caseName
     * @param sql 查询sql
     * @return
     */
    public String queryDataBase(String caseName, String env, String sql) {
        Database database= new Database();
        String str;
        try {
            LogHelper.stepExecLog("queryDataBase", caseName, sql);
            str = database.queryDataBase(caseName, env, sql);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Query database failed: " + ex));
        }
        LogHelper.stepAfterLog(caseName, str);
        return str;
    }

    /**
     * 对比缓存中的Json
     * @param expObj : 预期值缓存Key
     * @param atlObj : 实际值缓存Key
     *
     */
    public String verify(String caseName, String expObj, String atlObj) {
        Verify verify = new Verify();
        String errorMsg = "";
        try {
            LogHelper.stepExecLog("verify", expObj, atlObj);
            errorMsg = verify.verify(caseName, expObj.trim(), atlObj.trim());
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return errorMsg;
    }

    /**
     * 对比两个相同类型的实体
     * @param expObj : 预期结果值缓存Key
     * @param expObj : 实际结果值缓存Key
     * @param expObj : 对比实体List主键
     * @param atlObj : 实体中不对比的字段
     *
     */
    public String verify(String caseName, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr) {
        Verify verify = new Verify();
        String errorMsg = "";
        try {
            LogHelper.stepExecLog("verify", expObj, atlObj, pkMapStr, noCompareItemMapStr);
            errorMsg = verify.verify(caseName, expObj.trim(), atlObj.trim(), pkMapStr, noCompareItemMapStr);
            //deposed();
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
    public String verify(String caseName, String atlObj, String JsonFilter, String expValue) {
        Verify verify = new Verify();
        String errorMsg = "";
        try {
            LogHelper.stepExecLog("verify", atlObj, JsonFilter, expValue);
            errorMsg = verify.verify(caseName, atlObj.trim(), JsonFilter.trim(), expValue.trim());
            //deposed();
        }
        catch (Exception ex) {
            deposed();
            AssertionHelper.assertion(String.format("Verify object failed: " + ex));
        }
        return errorMsg;
    }

    /**
     * 追加缓存
     *
     * @param bufferKey
     * @param bufferVal
     * @return
     */
    public TestFlowManager appendBuffer(String bufferKey, String bufferVal) {
        try {
            LogHelper.stepExecLog("appendBuffer", bufferKey, bufferVal);
            BufferManager.appendBufferByKey(bufferKey, bufferVal);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("append Buffer key \"%s\" value \"%s\" failed: %s", bufferKey, bufferVal, ex));
        }
        return this;
    }

    /**
     * 添加缓存
     *
     * @param bufferKey
     * @param bufferVal
     * @return
     */
    public TestFlowManager addBuffer(String bufferKey, String bufferVal) {
        try {
            LogHelper.stepExecLog("addBuffer", bufferKey, bufferVal);
            BufferManager.addBufferByKey(bufferKey, bufferVal);
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
    public String getBuffer(String bufferKey) {
        String str = "";
        try {
            LogHelper.stepExecLog("getBuffer", bufferKey);
            str = BufferManager.getBufferByKey(bufferKey);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("add Buffer key \"%s\" value \"%s\" failed: %s", bufferKey));
        }
        return str;
    }
}

