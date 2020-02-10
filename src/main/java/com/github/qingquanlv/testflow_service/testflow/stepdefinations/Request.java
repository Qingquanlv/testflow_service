package com.github.qingquanlv.testflow_service.testflow.stepdefinations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
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

    //content type
    public static final String JSON = "json";
    public static final String XML = "xml";

    //HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    public static final String CONTENT_TYPE_XML="text/xml";

    /**
     * 发送XML Post请求
     *
     */
    public String sendRequest(String requestXmlStr, String requestType, String contentType, String url) throws Exception {
        String requestObject;

        requestXmlStr = ParamUtil.parseParam(requestXmlStr);
        logger.info(String.format("%s Send request: %s", new Date(), requestXmlStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        if (JSON.equals(contentType)) {
            contentType = CONTENT_TYPE_JSON;
        }
        else {
            contentType = CONTENT_TYPE_XML;
        }
        if (POST.equals(requestType)) {
            requestObject = HttpClientUtil.sendHttpPost(url, requestXmlStr, contentType);
        }
        logger.info(String.format("%s Get responce: %s", new Date(), requestObject));
        return ParamUtil.parseJsonDate(requestObject);
    }

    /**
     * 批量发送XML Post请求
     *
     */
    public String sendBatchRequest(String requestXmlStr, String url) throws Exception {
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
        List<String> rsplist = HttpClientUtil.sendHttpPostList(url, requestJsonStrList, CONTENT_TYPE_XML);
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

