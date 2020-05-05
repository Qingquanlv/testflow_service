package com.github.qingquanlv.testflow_service.testflow;

import com.github.qingquanlv.testflow_service.DAO.StepBufferDao;
import com.github.qingquanlv.testflow_service.testflow.business.BufferManager;
import com.github.qingquanlv.testflow_service.testflow.business.LogHelper;
import com.github.qingquanlv.testflow_service.testflow.stepdefinations.Parser;
import com.github.qingquanlv.testflow_service.testflow.stepdefinations.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class TestFlowManager {

    private static class SingleHolder{
        private static TestFlowManager instance = new TestFlowManager();
    }

    /**
     * 私有化构造方法
     */
    private TestFlowManager(){

        BufferManager.initBufferMap();
        LogHelper.initLog();
    }

    /**
     * 初始化实例，初始化缓存
     *
     */
    public static TestFlowManager runner(){
        return SingleHolder.instance;
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
    public String sendRequest(String requestId, String requestStr, HashMap<String, String> config, HashMap<String, String> headerMap, String requestType, String contentType, String url) throws Exception  {
        Request request = new Request();
        StepBufferDao stepBufferDao = new StepBufferDao();
        String responceStr;
        LogHelper.stepExecLog("sendRequest", requestStr, url, requestId);
        responceStr = request.sendRequest(requestStr, config, headerMap, requestType, contentType, url);
        //BufferManager.addBufferByKey(requestId, responceStr);
        stepBufferDao.addStepBuffer(requestId);
        LogHelper.stepAfterLog(requestId, responceStr);
        return responceStr;
    }

    /**
     * 使用String格式函数Parse
     * @param convertMethodSource : parse函数字符串
     * @param convertMethodName : parse函数name
     * @param sourceParemKey : parse入参缓存Key
     * @param sourceParamType : parse入参类型
     * @param targetParemKey : parse函数返回值Key
     * @param targetParamType : parse返回值类型
     *
     */
    public TestFlowManager sourceParse(String convertMethodSource, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParamType, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("sourceParse", convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, targetParemKey, targetParamType);
        try {
            str = parser.parseValueVidStr(convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, targetParamType);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Init object failed"));
        }
        LogHelper.stepAfterLog(targetParemKey, str);
        return this;
    }

    /**
     *
     * @param convertMethodSource : parse函数字符串
     * @param convertMethodName : parse函数name
     * @param sourceParemKey : parse入参缓存Key
     * @param sourceParamType : parse入参类型
     * @param sourceParemKey2 : parse入参缓存Key2
     * @param sourceParamType2 : parse入参类型2
     * @param targetParemKey : parse函数返回值Key
     * @param targetParamType : parse返回值类型
     * @return
     */
    public TestFlowManager sourceParse(String convertMethodSource, String convertMethodName, String sourceParemKey, String sourceParamType, String sourceParemKey2, String sourceParamType2, String targetParamType, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("sourceParse", convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, targetParemKey, targetParamType);
        try {
            str = parser.parseValueVidStr(convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, targetParamType);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Init object failed"));
        }
        LogHelper.stepAfterLog(targetParemKey, str);
        return this;
    }

    /**
     * 使用String格式函数Parse
     * @param convertMethodSource : parse函数字符串
     * @param convertMethodName : parse函数name
     * @param sourceParemKey : parse入参缓存Key
     * @param sourceParamType : parse入参类型
     * @param sourceParemKey2 : parse入参缓存Key2
     * @param sourceParamType2 : parse入参类型2
     * @param sourceParemKey3 : parse入参缓存Key3
     * @param sourceParamType3 : parse入参类型3
     * @param targetParemKey : parse函数返回值Key
     * @param targetParamType : parse返回值类型
     *
     */
    public TestFlowManager sourceParse(String convertMethodSource, String convertMethodName, String sourceParemKey, String sourceParamType, String sourceParemKey2, String sourceParamType2, String sourceParemKey3, String sourceParamType3, String targetParamType, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("sourceParse", convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, sourceParemKey3, sourceParamType3, targetParamType);
        try {
            str = parser.parseValueVidStr(convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, sourceParemKey3, sourceParamType3, targetParamType);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Init object failed"));
        }
        LogHelper.stepAfterLog(targetParemKey, str);
        return this;
    }

    /**
     * 使用String格式函数Parse
     * @param convertMethodSource : parse函数字符串
     * @param convertMethodName : parse函数name
     * @param sourceParemKey : parse入参缓存Key
     * @param sourceParamType : parse入参类型
     * @param sourceParemKey2 : parse入参缓存Key2
     * @param sourceParamType2 : parse入参类型2
     * @param sourceParemKey3 : parse入参缓存Key3
     * @param sourceParamType3 : parse入参类型3
     * @param sourceParemKey4 : parse入参缓存Key4
     * @param sourceParamType4 : parse入参类型4
     * @param targetParemKey : parse函数返回值Key
     * @param targetParamType : parse返回值类型
     *
     */
    public TestFlowManager sourceParse(String convertMethodSource, String convertMethodName, String sourceParemKey, String sourceParamType, String sourceParemKey2, String sourceParamType2, String sourceParemKey3, String sourceParamType3, String sourceParemKey4, String sourceParamType4, String targetParamType, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("sourceParse", convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, sourceParemKey3, sourceParamType3, sourceParemKey4, sourceParamType4, targetParamType);
        try {
            str = parser.parseValueVidStr(convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, sourceParemKey3, sourceParamType3, sourceParemKey4, sourceParamType4, targetParamType);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Init object failed"));
        }
        LogHelper.stepAfterLog(targetParemKey, str);
        return this;
    }







    /**
     * 添加缓存
     *
     */
    public TestFlowManager addBuffer(String bufferKey, String bufferVal) {
        Buffer buffer = new Buffer();
        LogHelper.stepExecLog("addBuffer", bufferKey, bufferVal);
        try {
            buffer.addBufferByKey(bufferKey, bufferVal);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("add Buffer key \"%s\" value \"%s\" failed: %s", bufferKey, bufferVal, ex));
        }
        return this;
    }

    /**
     * 添加缓存
     *
     */
    public TestFlowManager addObjBuffer(String bufferKey, Object bufferVal) {
        Buffer buffer = new Buffer();
        LogHelper.stepExecLog("addBuffer", bufferKey, bufferVal.toString());
        try {
            String bufferStr = FastJsonUtil.toJson(bufferVal);
            buffer.addBufferByKey(bufferKey, bufferStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("add Buffer key \"%s\" value \"%s\" failed: %s", bufferKey, bufferVal, ex));
        }
        return this;
    }

    /**
     * 对比缓存中的Json
     * @param expObj : 预期值缓存Key
     * @param atlObj : 实际值缓存Key
     *
     */
    public TestFlowManager verify(String expObj, String atlObj) {
        Verify verify = new Verify();
        LogHelper.stepExecLog("verify", expObj, atlObj);
        try {
            String errorMsg = verify.verify(expObj, expObj);
            if (!"".equals(errorMsg)) {
                deposed();
                throw new AssertionError(String.format("\n" + errorMsg));
            }
        }
        catch (Exception ex) {
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return this;
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
    public TestFlowManager verify(String paramType, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr) {
        Verify verify = new Verify();
        LogHelper.stepExecLog("verify", paramType, expObj, atlObj, pkMapStr, noCompareItemMapStr);
        try {
            String errorMsg = verify.verify(paramType, expObj, atlObj, pkMapStr, noCompareItemMapStr);
            if (!"".equals(errorMsg)) {
                deposed();
                throw new AssertionError(String.format("\n" + errorMsg));
            }
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return this;
    }

    /**
     * 检验缓存中的Json某个key值
     * @param atlObj : 实际结果值缓存Key
     * @param JsonFilter : 定位Json的xpath
     * @param expValue : 特定key的预期值
     *
     */
    public TestFlowManager verify(String atlObj, String JsonFilter, String expValue) {
        Verify verify = new Verify();
        LogHelper.stepExecLog("verify", atlObj, JsonFilter, expValue);
        try {
            String errorMsg = verify.verify(atlObj, JsonFilter, expValue);
            if (!"".equals(errorMsg)) {
                deposed();
                throw new AssertionError(String.format("\n" + errorMsg));
            }
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return this;
    }

    /**
     * 通过myBatis XML查询DB
     *
     * @param queryKey sql语句id
     * @param param sql map 参数
     * @param bufferKey 存入缓存中的key
     * @return
     */
    public TestFlowManager queryDataBase(String queryKey, String param, String bufferKey) {
        Database database= new Database();
        String str;
        LogHelper.stepExecLog("queryDataBase", queryKey, param, bufferKey);
        try {
            str = database.queryDataBase(queryKey, param);
            BufferManager.addBufferByKey(bufferKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Query datebase failed: " + ex));
        }
        LogHelper.stepAfterLog(bufferKey, str);
        return this;
    }

    /**
     * 通过原生SQL语句查询DB
     *
     * @param bufferKey 缓存key
     * @param sql sql map 参数
     * @return
     */
    public TestFlowManager queryDataBase(String bufferKey, String sql) {
        Database database= new Database();
        String str;
        LogHelper.stepExecLog("queryDataBase", bufferKey, sql);
        try {
            str = database.queryDataBase(sql);
            BufferManager.addBufferByKey(bufferKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Query datebase failed: " + ex));
        }
        LogHelper.stepAfterLog(bufferKey, str);
        return this;
    }
}

