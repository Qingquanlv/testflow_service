package com.github.qingquanlv.testflow_service_biz.stepdefinations;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.qingquanlv.testflow_service_biz.common.BufferManager;
import com.github.qingquanlv.testflow_service_biz.common.Constants;
import com.github.qingquanlv.testflow_service_biz.serviceaccess.JavaStringCompiler;
import com.github.qingquanlv.testflow_service_biz.serviceaccess.ServiceAccess;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import com.github.qingquanlv.testflow_service_biz.utilities.JAXBUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {



    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueVidStr(String caseName, String convertMethodSource, List<String> paramList) throws Exception
    {
        //方法参数
        String parameterStr = "";
        for (int i=0;i<paramList.size();i++) {
            String parameterType = "String";
            String parameterName = paramList.get(i);
            String js = "parameterType parameterName = (parameterType)JSON.parse(BufferManager.getBufferByKey(sourceData.get(index)));";
            String s = "String parameterName = BufferManager.getBufferByKey(sourceData.get(index));";
            String jsonStr = BufferManager.getBufferByKey(paramList.get(i));
            try {
                Object object = JSON.parse(jsonStr);
                if (object instanceof JSONObject) {
                    parameterType = Constants.JSON_OBJECT_TYPE;
                } else if (object instanceof JSONArray) {
                    parameterType = Constants.JSON_ARRAY_TYPE;
                }
                js = js.replace(Constants.INDEX, String.valueOf(i));
                js = js.replace(Constants.PARAMETER_TYPE, parameterType);
                js = js.replace(Constants.PARAMETER_NAME, parameterName);
                parameterStr += js;
            }
            catch (Exception ex) {
                s = s.replace(Constants.INDEX, String.valueOf(i));
                s = s.replace(Constants.PARAMETER_NAME, parameterName);
                parameterStr += s;
            }
        }

        String destJson = "";
        //初始化JavaCompliler(生成文件)
        JavaStringCompiler compiler = new JavaStringCompiler();
        //获取需要转化的类名和函数名
        String className = Constants.PARSE_VALUE_FILE_NAME;

        try {
            String convertFileSource = Constants.PARSE_VALUE_FILE_SOURCE.replace(Constants.PARAMETER, parameterStr)
                    .replace(Constants.METHOD, convertMethodSource);
            BufferManager.addConfigByKey(caseName,
                    String.format("method:%s",
                            convertFileSource));
            Map<String, byte[]> results = compiler.compile(className, convertFileSource);
            Class<?> clazz = compiler.loadClass(Constants.SERVICES_CLASS_PATH, results);
            Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, Constants.METHOD_NAME, List.class);
            destJson = String.valueOf(fieldGetMet.invoke(clazz.newInstance(), paramList));
        } catch (Exception e) {
            System.out.println("执行Convert方法错误" + e);
        }
        return destJson;
    }

    /**
     *
     * 获取List中类型
     *
     * @param value List类型
     * @return
     */
    private static String getListItemType(String value)
    {
        String targetStr;
        String pattern = "List<(.*)>";
        Pattern r = Pattern.compile(pattern);
        Matcher ma = r.matcher(value);
        boolean rs = ma.find();
        Pattern.matches(pattern, value);
        targetStr = ma.group(1);
        return targetStr;
    }

    public Object getParameType(String param, String paramType) throws Exception {
        String patten = "list<";
        Object sourceParam;
        Class<?> paramTypeClazz;
        if (paramType.toLowerCase().contains(patten)) {
            paramTypeClazz = ServiceAccess.reflectClazz(getListItemType(paramType));
            try {
                sourceParam = FastJsonUtil.toListBean(BufferManager.getBufferByKey(param), paramTypeClazz);
            }
            catch (Exception ex) {
                System.out.println(ex);
                sourceParam = JAXBUtil.formXMLList(BufferManager.getBufferByKey(param), paramTypeClazz);
            }
        }
        else if ("string".equals(paramType)) {
            sourceParam = BufferManager.getBufferByKey(param);
        }
        else {
            paramTypeClazz = ServiceAccess.reflectClazz(paramType);
            sourceParam = FastJsonUtil.toBean(BufferManager.getBufferByKey(param), paramTypeClazz);
        }
        return sourceParam;
    }

    public Class<?> getParameReflectType(String param) throws Exception {
        String patten = "list<";
        Class<?> paramTypeClazz;
        if (param.toLowerCase().contains(patten)) {
            paramTypeClazz = java.util.List.class;
        }
        else if("string".equals(param)) {
            paramTypeClazz = String.class;
        }
        else {
            paramTypeClazz = ServiceAccess.reflectClazz(param);
        }
        return paramTypeClazz;
    }

    public String getParamImportType(String param) throws Exception {
        String patten = "list<";
        String paramImportType;
        if (param.toLowerCase().contains(patten)) {
            paramImportType = getListItemType(param);
        }
        else {
            paramImportType = param;
        }
        return paramImportType;
    }
}




