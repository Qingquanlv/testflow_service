package com.github.qingquanlv.testflow_service_biz.stepdefinations;

import com.github.qingquanlv.testflow_service_biz.common.BufferManager;
import com.github.qingquanlv.testflow_service_biz.common.Constants;
import com.github.qingquanlv.testflow_service_biz.serviceaccess.JavaStringCompiler;
import com.github.qingquanlv.testflow_service_biz.serviceaccess.ServiceAccess;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import com.github.qingquanlv.testflow_service_biz.utilities.JAXBUtil;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueVidStr(String convertMethodSource, String convertMethodName, String targetDataParamType, String sourceData, String sourceDataParamType) throws Exception
    {
        String targetJson = "";
        //初始化JavaCompliler(生成文件)
        JavaStringCompiler compiler = new JavaStringCompiler();
        //获取需要转化的类名和函数名
        String className = Constants.PARSE_VALUE_FILE_NAME;

        try {
            Object sourceParam = getParameType(sourceData, sourceDataParamType);
            Class<?> sourceParamReflectType = getParameReflectType(sourceDataParamType);
            String convertFileSource = Constants.PARSE_VALUE_FILE_SOURCE.replace(Constants.PARAMTYPE1, getParamImportType(sourceDataParamType))
                    .replace(Constants.PARAMTYPE2, getParamImportType(targetDataParamType))
                    .replace(Constants.METHOD, convertMethodSource);
            Map<String, byte[]> results = compiler.compile(className, convertFileSource);
            Class<?> clazz = compiler.loadClass(Constants.SERVICES_CLASS_PATH, results);
            Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceParamReflectType);
            targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParam));
        } catch (Exception e) {
            System.out.println("执行Convert方法错误" + e);
        }
        return targetJson;
    }

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueVidStr(String convertMethodSource, String convertMethodName, String targetDataParamType, String sourceData, String sourceDataParamType, String sourceData2, String sourceDataParamType2) throws Exception
    {
        String targetJson = "";
        //初始化JavaCompliler(生成文件)
        JavaStringCompiler compiler = new JavaStringCompiler();
        //获取需要转化的类名和函数名
        String className = Constants.PARSE_VALUE_FILE_NAME;

        try {
            Object sourceParam = getParameType(sourceData, sourceDataParamType);
            Class<?> sourceParamReflectType = getParameReflectType(sourceDataParamType);
            Object sourceParam2 = getParameType(sourceData2, sourceDataParamType2);
            Class<?> sourceParamReflectType2 = getParameReflectType(sourceDataParamType2);
            String convertFileSource = Constants.PARSE_VALUE_FILE_SOURCE.replace(Constants.PARAMTYPE1, getParamImportType(sourceDataParamType))
                    .replace(Constants.PARAMTYPE2, getParamImportType(targetDataParamType))
                    .replace(Constants.METHOD, convertMethodSource);
            Map<String, byte[]> results = compiler.compile(className, convertFileSource);
            Class<?> clazz = compiler.loadClass(Constants.SERVICES_CLASS_PATH, results);
            Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceParamReflectType, sourceParamReflectType2);
            targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParam, sourceParam2));
        } catch (Exception e) {
            System.out.println("执行Convert方法错误" + e);
        }
        return targetJson;
    }

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueVidStr(String convertMethodSource, String convertMethodName, String targetDataParamType, String sourceData, String sourceDataParamType, String sourceData2, String sourceDataParamType2, String sourceData3, String sourceDataParamType3) throws Exception
    {
        String targetJson = "";
        //初始化JavaCompliler(生成文件)
        JavaStringCompiler compiler = new JavaStringCompiler();
        //获取需要转化的类名和函数名
        String className = Constants.PARSE_VALUE_FILE_NAME;

        try {
            Object sourceParam = getParameType(sourceData, sourceDataParamType);
            Class<?> sourceParamReflectType = getParameReflectType(sourceDataParamType);
            Object sourceParam2 = getParameType(sourceData2, sourceDataParamType2);
            Class<?> sourceParamReflectType2 = getParameReflectType(sourceDataParamType2);
            Object sourceParam3 = getParameType(sourceData3, sourceDataParamType3);
            Class<?> sourceParamReflectType3 = getParameReflectType(sourceDataParamType3);
            String convertFileSource = Constants.PARSE_VALUE_FILE_SOURCE.replace(Constants.PARAMTYPE1, getParamImportType(sourceDataParamType))
                    .replace(Constants.PARAMTYPE2, getParamImportType(targetDataParamType))
                    .replace(Constants.METHOD, convertMethodSource);
            Map<String, byte[]> results = compiler.compile(className, convertFileSource);
            Class<?> clazz = compiler.loadClass(Constants.SERVICES_CLASS_PATH, results);
            Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceParamReflectType, sourceParamReflectType2, sourceParamReflectType3);
            targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParam, sourceParam2, sourceParam3));
        } catch (Exception e) {
            System.out.println("执行Convert方法错误" + e);
        }
        return targetJson;
    }

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueVidStr(String convertMethodSource, String convertMethodName, String targetDataParamType, String sourceData, String sourceDataParamType, String sourceData2, String sourceDataParamType2, String sourceData3, String sourceDataParamType3, String sourceData4, String sourceDataParamType4) throws Exception
    {
        String targetJson = "";
        //初始化JavaCompliler(生成文件)
        JavaStringCompiler compiler = new JavaStringCompiler();
        //获取需要转化的类名和函数名
        String className = Constants.PARSE_VALUE_FILE_NAME;

        try {
            Object sourceParam = getParameType(sourceData, sourceDataParamType);
            Class<?> sourceParamReflectType = getParameReflectType(sourceDataParamType);
            Object sourceParam2 = getParameType(sourceData2, sourceDataParamType2);
            Class<?> sourceParamReflectType2 = getParameReflectType(sourceDataParamType2);
            Object sourceParam3 = getParameType(sourceData3, sourceDataParamType3);
            Class<?> sourceParamReflectType3 = getParameReflectType(sourceDataParamType3);
            Object sourceParam4 = getParameType(sourceData4, sourceDataParamType4);
            Class<?> sourceParamReflectType4 = getParameReflectType(sourceDataParamType4);
            String convertFileSource = Constants.PARSE_VALUE_FILE_SOURCE.replace(Constants.PARAMTYPE1, getParamImportType(sourceDataParamType))
                    .replace(Constants.PARAMTYPE2, getParamImportType(targetDataParamType))
                    .replace(Constants.METHOD, convertMethodSource);
            Map<String, byte[]> results = compiler.compile(className, convertFileSource);
            Class<?> clazz = compiler.loadClass(Constants.SERVICES_CLASS_PATH, results);
            Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceParamReflectType, sourceParamReflectType2, sourceParamReflectType3, sourceParamReflectType4);
            targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParam, sourceParam2, sourceParam3, sourceParam4));
        } catch (Exception e) {
            System.out.println("执行Convert方法错误" + e);
        }
        return targetJson;
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




