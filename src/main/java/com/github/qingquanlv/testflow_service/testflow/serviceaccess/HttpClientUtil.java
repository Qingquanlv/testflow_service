package com.github.qingquanlv.testflow_service.testflow.serviceaccess;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;

import java.net.InetAddress;
import java.util.*;

public class HttpClientUtil {
    //Socket超时时间
    public static final String SOCKETTIMEOUT = "sockettimeout";
    //连接超时时间
    public static final String CONNECTTIMEOUT = "connecttimeout";
    //请求超时时间
    public static final String CONNECTIONREQUESTTIMEOUT = "connectionrequesttimeout";
    //本机
    public static final String LOCAL_HOST = "localhost";

    /**
     * 发送 Head请求
     * @param httpUrl 地址
     * @return
     */
    public static String sendHttpHead(String httpUrl, HashMap<String, String> headers, HashMap<String, String> config) {
        // 创建HttpHead
        HttpHead httpHead = new HttpHead(httpUrl);
        addHeaders(httpHead, headers);
        buildRequestConfig(config);
        String str = HttpClientImpl.sendHttp(httpHead);
        deposeRequestConfig();
        return str;
    }

    /**
     * 发送 Trace请求
     * @param httpUrl
     * @return
     */
    public static String sendHttpTrace(String httpUrl, HashMap<String, String> headers, HashMap<String, String> config) {
        // 创建HttpTrace
        HttpTrace httpTrace = new HttpTrace(httpUrl);
        addHeaders(httpTrace, headers);
        buildRequestConfig(config);
        String str = HttpClientImpl.sendHttp(httpTrace);
        deposeRequestConfig();
        return str;
    }

    /**
     * 发送 Options请求
     * @param httpUrl
     * @return
     */
    public static String sendHttpOptions(String httpUrl, HashMap<String, String> headers, HashMap<String, String> config) {
        // 创建HttpOptions
        HttpOptions httpOptions = new HttpOptions(httpUrl);
        addHeaders(httpOptions, headers);
        buildRequestConfig(config);
        String str = HttpClientImpl.sendHttp(httpOptions);
        deposeRequestConfig();
        return str;
    }

    /**
     * 发送 Delete请求
     *
     * @param httpUrl    地址
     */
    public static String sendHttpDelete(String httpUrl, HashMap<String, String> headers, HashMap<String, String> config) {
        // 创建httpPost
        HttpDelete httpDelete = new HttpDelete(httpUrl);
        addHeaders(httpDelete, headers);
        buildRequestConfig(config);
        String str = HttpClientImpl.sendHttp(httpDelete);
        deposeRequestConfig();
        return str;
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl, HashMap<String, String> headers, HashMap<String, String> config) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        addHeaders(httpGet, headers);
        buildRequestConfig(config);
        String str = HttpClientImpl.sendHttp(httpGet);
        deposeRequestConfig();
        return str;
    }

    /**
     * 发送 put请求 发送json数据
     *
     * @param httpUrl    地址
     * @param paramsJson 参数(格式 json)
     */
    public static String sendHttpPutJson(String httpUrl, String paramsJson, HashMap<String, String> headers, HashMap<String, String> config, String contentType) {
        // 创建httpPost
        HttpPut httpPut = new HttpPut(httpUrl);
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(contentType);
                addHeaders(httpPut, headers);
                buildRequestConfig(config);
                httpPut.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = HttpClientImpl.sendHttp(httpPut);
        deposeRequestConfig();
        return str;
    }

    /**
     * 批量发送post请求
     *
     * @param httpUrl
     * @param requestJsonStr
     * @return
     */
    public static List<String> sendHttpPutJsonList(String httpUrl, List<String> requestJsonStr, HashMap<String, String> headers, HashMap<String, String> config, String contentType) {
        List<String> responceJsonStrList = new ArrayList<>();
        for (String paramsJson : requestJsonStr) {
            // 创建httpPost
            HttpPut httpPut = new HttpPut(httpUrl);
            try {
                // 设置参数
                if (paramsJson != null && paramsJson.trim().length() > 0) {
                    StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                    stringEntity.setContentType(contentType);
                    addHeaders(httpPut, headers);
                    buildRequestConfig(config);
                    httpPut.setEntity(stringEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            responceJsonStrList.add(HttpClientImpl.sendHttp(httpPut));
        }
        deposeRequestConfig();
        return responceJsonStrList;
    }

    /**
     * 发送 HeadPatch请求
     * @param httpUrl 地址
     * @return
     */
    public static String sendHttpPatch(String httpUrl, String paramsJson, HashMap<String, String> headers, HashMap<String, String> config, String contentType) {
        // 创建HttpPatch
        HttpPatch httpPatch = new HttpPatch(httpUrl);
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(contentType);
                addHeaders(httpPatch, headers);
                buildRequestConfig(config);
                httpPatch.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = HttpClientImpl.sendHttp(httpPatch);
        deposeRequestConfig();
        return str;
    }

    /**
     * 发送 post请求 发送json数据
     *
     * @param httpUrl    地址
     * @param paramsJson 参数(格式 json)
     */
    public static String sendHttpPost(String httpUrl, String paramsJson, HashMap<String, String> headers, HashMap<String, String> config, String contentType) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(contentType);
                buildRequestConfig(config);
                addHeaders(httpPost, headers);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = HttpClientImpl.sendHttp(httpPost);
        deposeRequestConfig();
        return str;
    }

    /**
     * 批量发送post请求
     *
     * @param httpUrl
     * @param requestJsonStr
     * @return
     */
    public static List<String> sendHttpPostList(String httpUrl, List<String> requestJsonStr, HashMap<String, String> headers, String contentType) {
        List<String> responceJsonStrList = new ArrayList<>();
        for (String paramsJson : requestJsonStr) {
            // 创建httpPost
            HttpPost httpPost = new HttpPost(httpUrl);
            try {
                // 设置参数
                if (paramsJson != null && paramsJson.trim().length() > 0) {
                    StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                    stringEntity.setContentType(contentType);
                    buildRequestConfig(headers);
                    addHeaders(httpPost);
                    httpPost.setEntity(stringEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            responceJsonStrList.add(HttpClientImpl.sendHttp(httpPost));
        }
        deposeRequestConfig();
        return responceJsonStrList;
    }


    /**
     * 设置代理
     *
     * @param ipAddress
     * @param port
     * @param scheme
     */
    public static void setProxy(String ipAddress, int port, String scheme) throws Exception {
        HttpHost proxy;
        if (LOCAL_HOST.equals(ipAddress.trim().toLowerCase())) {
            InetAddress addr = InetAddress.getLocalHost();
            proxy = new HttpHost(addr, port, scheme);
        }
        else {
            proxy = new HttpHost(ipAddress, port, scheme);
        }
        RequestConfig.Builder config = RequestConfig.copy(HttpClientImpl.getRequestConfig());
        HttpClientImpl.requestConfig = config.setProxy(proxy).build();
    }

    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
     *
     * @param parameterMap 需要转化的键值对集合
     * @return 字符串
     */
    public static String convertStringParamter(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

    /**
     * 添加请求Header
     *
     * @param param
     * @param <T>
     */
    private static <T extends HttpRequestBase> void addHeaders(T param) {
        HashMap<String, String> map = HttpClientImpl.getHttpHeaderParam();
        if (map != null) {
            for (String key : map.keySet()) {
                param.addHeader(key, map.get(key));
            }
        }
    }

    /**
     * 添加请求Header
     *
     * @param param
     * @param <T>
     */
    private static <T extends HttpRequestBase> void addHeaders(T param, HashMap<String, String> map) {
        if (map != null) {
            for (String key : map.keySet()) {
                param.addHeader(key, map.get(key));
            }
        }
    }

    /**
     * 设置请求参数
     *
     */
    private static void buildRequestConfig(HashMap<String, String> configMap) {
        RequestConfig.Builder config = RequestConfig.copy(HttpClientImpl.getRequestConfig());
        // 根据默认超时限制初始化requestConfig
        int socketTimeout = 20000;
        int connectTimeout = 20000;
        int connectionRequestTimeout = 20000;
        if (null != configMap.get(SOCKETTIMEOUT)) {
            config.setSocketTimeout(Integer.parseInt(configMap.get(SOCKETTIMEOUT)));
        }
        else {
            config.setSocketTimeout(socketTimeout);
        }
        if (null != configMap.get(CONNECTTIMEOUT)) {
            config.setConnectTimeout(Integer.parseInt(configMap.get(CONNECTTIMEOUT)));
        }
        else {
            config.setConnectTimeout(connectTimeout);
        }
        if (null != configMap.get(CONNECTIONREQUESTTIMEOUT)) {
            config.setConnectionRequestTimeout(Integer.parseInt(configMap.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            config.setConnectionRequestTimeout(connectionRequestTimeout);
        }
        if (null != configMap.get(CONNECTIONREQUESTTIMEOUT)) {
            config.setConnectionRequestTimeout(Integer.parseInt(configMap.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            config.setConnectionRequestTimeout(connectionRequestTimeout);
        }
        if (null != configMap.get(CONNECTIONREQUESTTIMEOUT)) {
            config.setConnectionRequestTimeout(Integer.parseInt(configMap.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            config.setConnectionRequestTimeout(connectionRequestTimeout).build();
        }
        HttpClientImpl.requestConfig = config.build();
    }

    /**
     * 设置请求参数
     *
     */
    private static void deposeRequestConfig() {
        HttpClientImpl.requestConfig = null;
        HttpClientImpl.httpHeaderParam = null;
        HttpClientImpl.requestConfigParam = null;
    }
}

