package com.github.qingquanlv.testflow_service_biz.stepdefinations;

import com.github.qingquanlv.testflow_service_biz.serviceaccess.HttpClientUtil;
import com.github.qingquanlv.testflow_service_biz.utilities.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author qq.lv
 * @date 2019/6/15
 */
public class Request {

    private static Logger logger = LoggerFactory.getLogger(Request.class);
    //requestType
    public static final String POST = "post";
    public static final String HEAD = "head";
    public static final String GET = "get";
    public static final String PUT = "put";
    public static final String DELETE = "delete";
    public static final String OPTIONS = "options";
    public static final String TRACE = "trace";
    public static final String PATCH = "patch";

    //content type
    public static final String JSON = "json";
    public static final String XML = "xml";

    //HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    public static final String CONTENT_TYPE_XML="text/xml";

    /**
     *
     * @param requestStr : 请求body
     * @param config : 请求配置项
     * @param headerMap : 请求头
     * @param requestType : 请求类型 post/get
     * @param contentType : 请求body类型 xml/json
     * @param url : 请求url
     * @return
     * @throws Exception
     *
     */
    public String sendRequest(String requestStr, HashMap<String, String> config, HashMap<String, String> headerMap, String requestType, String contentType, String url) throws Exception {
        String requestObject;

        requestStr = null == requestStr ? "" : ParamUtil.parseParam(requestStr);
        logger.info(String.format("%s Send request: %s", new Date(), requestStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //request报文类型
        if (JSON.equals(contentType)) {
            contentType = CONTENT_TYPE_JSON;
        }
        else {
            contentType = CONTENT_TYPE_XML;
        }
        //requestType类型
        if (POST.equals(requestType)) {
            requestObject = HttpClientUtil.sendHttpPost(url, requestStr, headerMap, config, contentType);
        }
        else if (PATCH.equals(requestType)) {
            requestObject = HttpClientUtil.sendHttpPatch(url, requestStr, headerMap, config, contentType);
        }
        else if (GET.equals(requestType)) {
            requestObject = HttpClientUtil.sendHttpGet(url, headerMap, config);
        }
        else if (HEAD.equals(requestType)) {
            requestObject = HttpClientUtil.sendHttpHead(url, headerMap, config);
        }
        else if (PUT.equals(requestType)) {
            requestObject = HttpClientUtil.sendHttpPutJson(url, requestStr, headerMap, config, contentType);
        }
        else if (DELETE.equals(requestType)) {
            requestObject = HttpClientUtil.sendHttpDelete(url, headerMap, config);
        }
        else if (OPTIONS.equals(requestType)) {
            requestObject = HttpClientUtil.sendHttpOptions(url, headerMap, config);
        }
        else {
            requestObject = HttpClientUtil.sendHttpTrace(url, headerMap, config);
        }
        logger.info(String.format("%s Get responce: %s", new Date(), requestObject));
        return ParamUtil.parseJsonDate(requestObject);
    }

    /**
     * 批量发送XML Post请求
     *
     */
    public String sendBatchRequest(String requestXmlStr, HashMap<String, String> headers, String url) throws Exception {
        List<String> requestJsonStrList = ParamUtil.parseParamList(requestXmlStr);
        String responceJsonStrList = "<ListRoot>";
        //批量post json str
        List<String> list = new ArrayList<>();
        for (String str : requestJsonStrList) {
            list.add(ParamUtil.parseParam(str));
        }
        logger.info(String.format("%s Send request: %s", new Date(), list));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        List<String> rsplist = HttpClientUtil.sendHttpPostList(url, requestJsonStrList, headers, CONTENT_TYPE_XML);
        for (int i = 0; i < rsplist.size(); i++) {
            responceJsonStrList += rsplist.get(i);
        }
        responceJsonStrList += "</ListRoot>";
        logger.info(String.format("%s Get responce: %s", new Date(), responceJsonStrList));
        return ParamUtil.parseJsonDate(responceJsonStrList);
    }

    /**
     * 设置请求代理
     *
     */
    public void setProxy(String ipAddress, int port, String scheme) throws Exception {
        HttpClientUtil.setProxy(ipAddress, port, scheme);
    }
}

