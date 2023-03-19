package com.github.qingquanlv.testflow_service_biz.utilities;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.qingquanlv.testflow_service_biz.common.BufferManager;
import com.github.qingquanlv.testflow_service_biz.serviceaccess.ServiceAccess;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author qq.lv
 * @date 2019/6/22
 */
public class ParamUtil {
    private static Logger logger = LoggerFactory.getLogger(ParamUtil.class);

    public static Pattern paramPattern = Pattern.compile("\\$\\{.*?\\}");
    public static Pattern ConfigPattern = Pattern.compile("\\{\\{.*?\\}\\}");
    public static Pattern paramPatternNoXPath = Pattern.compile("\\$P\\{.*?\\}");
    public static Pattern listParamPattern = Pattern.compile("\\$L\\{.*?\\}");
    public static Pattern TimeDayPattern = Pattern.compile("(?i)\\{\\{CURRENTDAY(.*?)\\}\\}");
    public static Pattern TimeStampPattern = Pattern.compile("(?i)\\{\\{CURRENTDAYTIMESTAMP(.*?)\\}\\}");
    public static Pattern TimeStampMillisPattern = Pattern.compile("(?i)\\{\\{CURRENTDAYTIMESTAMPMILLIS(.*?)\\}\\}");
    public static Pattern TimeStampPatternDay = Pattern.compile("(?i)((?:\\-|\\+?)\\s*?\\d+)\\s*?DAY");
    public static Pattern TimeStampPatternHour = Pattern.compile("(?i)((?:\\-|\\+?)\\s*?\\d+)\\s*?HOUR");
    public static Pattern TimeStampPatternMinute = Pattern.compile("(?i)((?:\\-|\\+?)\\s*?\\d+)\\s*?MINUTE");
    public static Pattern TimeStampPatternSecond = Pattern.compile("(?i)((?:\\-|\\+?)\\s*?\\d+)\\s*?SECOUND");
    public static Pattern TimeStampPatternTimeZone = Pattern.compile("(?i)((?:\\-|\\+?)\\s*?\\d+)\\s*?TIMEZONE");
    public static Pattern TimeStampPatternTimeFormat = Pattern.compile("\\,\\s*(\\.+)\\s*\\}");

    public static Pattern mapPatternStr = Pattern.compile("^\\{(.*?)\\}$");
    public static String patternStr = ".*\\$\\{(.*?)\\}.*";
    public static String configPatternStr = ".*\\{\\{(.*?)\\}\\}.*";
    public static String listPatternStr = ".*\\$L\\{(.*?)\\}.*";
    public static String patternStrNoXPath = ".*\\$P\\{(.*?)\\}.*";



    /**
     * 转化字符串中参数
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParam(String val) throws Exception
    {
        val = parseParamXpath(val);
        val = parseParamNoXpath(val);
        val = parseTimeStampMillis(val);
        val = parseTimeStamp(val);
        val = parseTime(val);
        val = parseEnter(val);
        return val;
    }

    /**
     * 转化字符串中配置
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseConfig(String caseType, String val) throws Exception
    {
        val = parseParamConfig(caseType, val);
        return val;
    }

    /**
     * 根据path获取value
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParamPath(String val) throws Exception {
        String mapVal = "";
        String[] bufferKeyAndValue = getBufferKeyAndValue(val);
        if (bufferKeyAndValue.length >= 2) {
            //从缓存中获取数据
            String key = BufferManager.getBufferByKey(bufferKeyAndValue[0]);
            mapVal = getMapValFromJson(key, bufferKeyAndValue[1]);
            if (null == mapVal || mapVal.isEmpty()) {
                throw new Exception(String.format("No matched value for key \"%s\" Json string \"%s\" .", bufferKeyAndValue[1], key));
            }
        }
        return mapVal;
    }

    /**
     * 转化字符串中Xpath类型参数
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParamXpath(String val) throws Exception
    {
        //获取字符串中所有参数
        List<String> paramList = catchParamList(paramPattern, val);
        for(String param : paramList)
        {
            //转化参数为某缓存内的实体的属性值
            String paramConverted = convertParam(patternStr, param);
            String[] bufferKeyAndValue = getBufferKeyAndValue(paramConverted);
            if (bufferKeyAndValue.length < 2) { break; }
            //从缓存中获取数据
            String key = BufferManager.getBufferByKey(bufferKeyAndValue[0]);
            String mapVal = getMapValFromJson(key, bufferKeyAndValue[1]);
            if (null == mapVal || mapVal.isEmpty()) {
                throw new Exception(String.format("No matched value for key \"%s\" Json string \"%s\" .", bufferKeyAndValue[1], key));
            }
            else {
                val = updateParameStr(val, param, mapVal);
            }
        }
        return val;
    }

    /**
     * 转化字符串中非Xpath类型参数
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParamNoXpath(String val) throws Exception
    {
        //获取字符串中所有参数
        List<String> paramList = catchParamList(paramPattern, val);
        for(String param : paramList)
        {
            //转化参数为某缓存内的实体的属性值
            String key = convertParam(patternStr, param);
            String mapVal = BufferManager.getBufferByKey(key);
            if (null == mapVal) {
                throw new Exception(String.format("No matched value for key \"%s\" string \"%s\".", param, key));
            }
            else {
                val = updateParameStr(val, param, mapVal);
            }
        }
        return val;
    }

    /**
     * 转化字符串中配置类型参数
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParamConfig(String caseType, String val) throws Exception
    {
        val = parseParam(caseType, val);
        return val;
    }

    /**
     * 转化字符串中配置类型参数
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParam(String caseType, String val) throws Exception
    {
        //获取字符串中所有参数
        List<String> paramList = catchParamList(ConfigPattern, val);
        for(String param : paramList)
        {
            //转化参数为某缓存内的实体的属性值
            String key = convertParam(configPatternStr, param);
            String mapVal = BufferManager.getBufferByKey(
                    String.format("env$$config$$%s$$%s",
                            caseType, key));
            //判断时间参数
            if (null != mapVal) {
                val = updateParameStr(val, param, mapVal);
            }
        }
        return val;
    }

    /**
     * 转化字符串中时间戳
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseTime(String val) throws Exception {

        //获取字符串中所有参数
        List<String> timeStamps = catchParamList(TimeDayPattern, val);
        for (String time : timeStamps) {
            //处理format string
            List<String> timeFormatList = catchParamList(TimeStampPatternTimeFormat, time);
            String timeFormat
                    = timeFormatList.size() > 0
                    ? parseSpace(convertParam(TimeStampPatternTimeZone,timeFormatList.get(0)))
                    : "YYYY-MM-DD";
            //处理时区
            List<String> timeZoneList = catchParamList(TimeStampPatternTimeZone, time);
            Long timeZone
                    = timeZoneList.size() > 0
                    ? Long.parseLong(parseSpace(convertParam(TimeStampPatternTimeZone,timeZoneList.get(0))))
                    : 0L;
            long now = System.currentTimeMillis() / 1000L;
            long daySecond = 60 * 60 * 24;
            long dayTime = now - (now + timeZone * 3600) % daySecond;
            //转化参数为某缓存内的实体的属性值
            List<String> hours = catchParamList(TimeStampPatternHour, time);
            List<String> minutes = catchParamList(TimeStampPatternMinute, time);
            List<String> days = catchParamList(TimeStampPatternDay, time);
            List<String> seconds = catchParamList(TimeStampPatternSecond, time);

            for (String day : days) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternDay, day);
                Long hourNum = Long.valueOf(parseSpace(key));
                Long mapVal = hourNum * 60 * 60 * 24;
                dayTime = dayTime + mapVal;
            }
            for (String hour : hours) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternHour, hour);
                Long minuteNum = Long.valueOf(parseSpace(key));
                Long mapVal = minuteNum * 60 * 60;
                dayTime = dayTime + mapVal;
            }
            for (String minute : minutes) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternMinute, minute);
                Long minuteNum = Long.valueOf(parseSpace(key));
                Long mapVal = minuteNum * 60;
                dayTime = dayTime + mapVal;
            }
            for (String second : seconds) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternSecond, second);
                Long SecondNum = Long.valueOf(parseSpace(key));
                Long mapVal = SecondNum;
                dayTime = dayTime + mapVal;
            }
            SimpleDateFormat formatYYYY = new SimpleDateFormat(timeFormat);
            String result = formatYYYY.format(new Date(dayTime * 1000));
            val = updateParameStr(val, time, result);
        }
        return val;
    }



    /**
     * 转化字符串中时间戳
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseTimeStampMillis(String val) throws Exception {
        //获取字符串中所有参数
        List<String> timeStamps = catchParamList(TimeStampMillisPattern, val);

        for (String time : timeStamps) {
            List<String> timeZoneList = catchParamList(TimeStampPatternTimeZone, time);
            Long timeZone
                    = timeZoneList.size() > 0
                    ? Long.parseLong(parseSpace(convertParam(TimeStampPatternTimeZone,timeZoneList.get(0))))
                    : 0L;
            long now = System.currentTimeMillis();
            long daySecond = 60 * 60 * 24 * 1000;
            long dayTime = now - (now + timeZone * 3600 * 1000) % daySecond;
            //转化参数为某缓存内的实体的属性值
            List<String> hours = catchParamList(TimeStampPatternHour, time);
            List<String> minutes = catchParamList(TimeStampPatternMinute, time);
            List<String> days = catchParamList(TimeStampPatternDay, time);
            List<String> seconds = catchParamList(TimeStampPatternSecond, time);

            for (String day : days) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternDay, day);
                Long hourNum = Long.valueOf(parseSpace(key));
                Long mapVal = hourNum * 60 * 60 * 24 * 1000;
                dayTime = dayTime + mapVal;
            }
            for (String hour : hours) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternHour, hour);
                Long minuteNum = Long.valueOf(parseSpace(key));
                Long mapVal = minuteNum * 60 * 60 * 1000;
                dayTime = dayTime + mapVal;
            }
            for (String minute : minutes) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternMinute, minute);
                Long minuteNum = Long.valueOf(parseSpace(key));
                Long mapVal = minuteNum * 60 * 1000;
                dayTime = dayTime + mapVal;
            }
            for (String second : seconds) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternSecond, second);
                Long SecondNum = Long.valueOf(parseSpace(key));
                Long mapVal = SecondNum * 1000;
                dayTime = dayTime + mapVal;
            }
            val = updateParameStr(val, time, String.valueOf(dayTime));
        }
        return val;

    }

    /**
     * 转化字符串中时间戳
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseTimeStamp(String val) throws Exception {
        //获取字符串中所有参数
        List<String> timeStamps = catchParamList(TimeStampPattern, val);

        for (String time : timeStamps) {
            List<String> timeZoneList = catchParamList(TimeStampPatternTimeZone, time);
            Long timeZone
                    = timeZoneList.size() > 0
                    ? Long.parseLong(parseSpace(convertParam(TimeStampPatternTimeZone,timeZoneList.get(0))))
                    : 0L;
            long now = System.currentTimeMillis() / 1000L;
            long daySecond = 60 * 60 * 24;
            long dayTime = now - (now + timeZone * 3600) % daySecond;
            //转化参数为某缓存内的实体的属性值
            List<String> hours = catchParamList(TimeStampPatternHour, time);
            List<String> minutes = catchParamList(TimeStampPatternMinute, time);
            List<String> days = catchParamList(TimeStampPatternDay, time);
            List<String> seconds = catchParamList(TimeStampPatternSecond, time);

            for (String day : days) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternDay, day);
                Long hourNum = Long.valueOf(parseSpace(key));
                Long mapVal = hourNum * 60 * 60 * 24;
                dayTime = dayTime + mapVal;
            }
            for (String hour : hours) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternHour, hour);
                Long minuteNum = Long.valueOf(parseSpace(key));
                Long mapVal = minuteNum * 60 * 60;
                dayTime = dayTime + mapVal;
            }
            for (String minute : minutes) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternMinute, minute);
                Long minuteNum = Long.valueOf(parseSpace(key));
                Long mapVal = minuteNum * 60;
                dayTime = dayTime + mapVal;
            }
            for (String second : seconds) {
                //转化参数为某缓存内的实体的属性值
                String key = convertParam(TimeStampPatternSecond, second);
                Long SecondNum = Long.valueOf(parseSpace(key));
                Long mapVal = SecondNum;
                dayTime = dayTime + mapVal;
            }
            val = updateParameStr(val, time, String.valueOf(dayTime));
        }
        return val;

    }

    /**
     * 去除字符串中的换行符
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseEnter(String val) throws Exception
    {
        return val.replace("\n", "").replace("\r", "").replace("\t", "");
    }

    /**
     * 去除字符串中的空格
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseSpace(String val) throws Exception
    {
        return val.replace(" ", "");
    }

    /**
     * 根据正则匹配，获取参数值
     *
     * @param value : 参数字符串
     * @return String ：返回参数
     */
    private static String convertParam(String pattern, String value)
    {
        Pattern r = Pattern.compile(pattern);
        Matcher ma = r.matcher(value);
        boolean rs = ma.find();
        if (Pattern.matches(pattern, value)) {
            value = ma.group(1);
        }
        return value;
    }

    /**
     * 获取字符串中的参数值
     *
     * @param value : 参数字符串
     * @return List<String> ：返回匹配的所有参数
     */
    private static String convertParam(Pattern pattern, String value){
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            value = matcher.group(1);
        }
        return value;
    }

    /**
     * 根据字符串格式XX：XX，拆分出XX，XX
     *
     * @param param : 参数字符串
     * @return String[] ：返回key，value string数组
     */
    private static String[] getBufferKeyAndValue(String param)
    {
        String[] bufferKeyAndValue = param.split(":");
        if(bufferKeyAndValue.length != 2)
        {
            //System.out.println(String.format("param \"%s\" type is not invalid.", param));
        }
        return bufferKeyAndValue;
    }

    /**
     * 获取字符串中的参数值
     *
     * @param value : 参数字符串
     * @return List<String> ：返回匹配的所有参数
     */
    private static List<String> catchParamList(Pattern pattern, String value){
        Matcher matcher = pattern.matcher(value);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 获取字符串中的参数值
     *
     * @param value : 参数字符串
     * @return List<String> ：返回匹配的所有参数
     */
    private static String catchParamMap(String value){
        Matcher matcher = mapPatternStr.matcher(value);
        matcher.find();
        return matcher.group(1);
    }

    /**
     * 通过JsonPath匹配获取Json串
     *
     * @param jsonStr : 要匹配的Json字符串
     * @param mapKey : 匹配的xpath字符串
     * @return String ：返回匹配后的Json串（1，2，3）
     */
    public static String getMapValFromJson(String jsonStr, String mapKey)
    {
        String str = "";
        Configuration conf = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                .options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS).build();

        ArrayNode node = JsonPath.using(conf).parse(jsonStr).read(mapKey);
        if (null != node) {
            for (Object item : node) {
                if (null != str && !"".equals(str)) {
                    str = String.format("%s,%s", str, item.toString().replaceAll("\"",""));
                }
                else {
                    if (null != str) {
                        str = item.toString().replaceAll("\"","");
                    }
                }
            }
        }
        return str;
    }

    /**
     * 更新字符串中的参数字符串
     *
     * @param sourceStr
     * @param matchMapKey
     * @param matchMapVal
     * @return
     */
    private static String updateParameStr(String sourceStr, String matchMapKey, String matchMapVal)
    {
        return sourceStr.replace(matchMapKey, matchMapVal);
    }

    /**
     * 通过Xpath匹配获取String串
     *
     * @param str
     * @param mapKey
     * @return

    public static List<Object> getMapValFromStr(String str, String mapKey)
    {
        List<Object> objlist;
        try {
            objlist = getMapValFromJson(str, mapKey);
        }
        catch (Exception e)
        {
            try {
                objlist = getMapValFromXMl(str, mapKey);
            }
            catch (Exception ex) {
                throw new RuntimeException(String.format("\"%s\" is not a legal string.", mapKey));
            }
        }
        if (null != objlist) {
            objlist.stream().forEach(item->item.toString().trim());
        }
        return objlist;
    }
    */


    /**
     * 通过Zson匹配获取Json串
     *
     * @param jsonStr : 要匹配的Json字符串
     * @param mapKey : 匹配的xpath字符串
     * @return String ：返回匹配后的Json串

    public static List<Object> getMapValFromJson(String jsonStr, String mapKey)
    {
        ZsonResult zr = ZSON.parseJson(jsonStr);
        zr.getClassTypes();
        List<Object> names = zr.getValues(mapKey.trim());
        return names;
    }
    */

    /**
     * 根据匹配Josn，构建多个的请求
     *
     * @param val : 请求参数模板
     * @return String ：返回请求List
     */
    public static List<String> parseParamList(String val) throws Exception
    {
        val = parseEnter(val);
        //List情况request只能存在一个参数, 若为多个默认取第一个
        List<String> paramList = catchParamList(listParamPattern, val);
        List<String> reqList = new ArrayList<>();
        for (String param : paramList) {
            if (!paramList.isEmpty()) {
                //去掉参数中的大括号
                String paramCovered = convertParam(listPatternStr, param);
                String[] bufferKeyAndValue = getBufferKeyAndValue(paramCovered);
                //从缓存中获取数据
                String str = BufferManager.getBufferByKey(bufferKeyAndValue[0]);
                List<Object> objList = getMapValFromStr(str, bufferKeyAndValue[1]);
                if (objList.isEmpty()) {
                    System.out.println(String.format("No matiched value for key \"%s\" Json string \"%s\" .", bufferKeyAndValue[1], str));
                }
                else {
                    int i = 0;
                    for (Object value : objList) {
                        if (reqList.size() > i) {
                            reqList.set(i, updateParameStr(reqList.get(i), param, value.toString()));
                        }
                        else {
                            reqList.add(updateParameStr(val, param, objList.get(i).toString()));
                        }
                        i++;
                    }
                }
            }
        }
        return reqList;
    }


    /**
     * 通过JsonPath匹配获取Json串
     *
     * @param jsonStr : 要匹配的Json字符串
     * @param mapKey : 匹配的xpath字符串
     * @return String ：返回匹配后的Json串（List<Object>）
     */
    public static List<Object> getMapValFromStr(String jsonStr, String mapKey)
    {
        List<Object> objList = new ArrayList<>();
        Configuration conf = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                .options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS).build();

        ArrayNode node = JsonPath.using(conf).parse(jsonStr).read(mapKey);
        if (null != node) {
            for (Object item : node) {
                objList.add(item);
            }
        }
        return objList;
    }



    /**
     * 通过Dom4J匹配获取XML串
     *
     * @param xmlStr : 要匹配的xml字符串
     * @param mapKey : 匹配的xpath字符串
     * @return  ：返回匹配后的xml串
     */
    public static List<Object> getMapValFromXMl(String xmlStr, String mapKey)
    {
        List<Object> objlist = new ArrayList<>();
        try {
            Document doc = DocumentHelper.parseText(xmlStr);
            List<Node> list = doc.selectNodes(mapKey);

            for (Node ele : list) {
                objlist.add(ele.getText());
            }
        } catch (Exception e) {
            throw new RuntimeException("这不是一个合法的XML串!" + e);
        }
        return objlist;

    }

    /**
     * 通过A:B:C格式匹配实体
     *
     * @param obj : 要匹配的对象实体
     * @param mapKey : 匹配的mapKey字符串
     * @return Object ：返回匹配后的实例
     */
    private static Object getMapValFromInstance(Object obj, String mapKey)
    {
        String[] keyList = mapKey.split("\\/");
        for (String key : keyList) {
            if (!"".equals(key)) {
                if ("".equals(isListItem(key))) {}
                Method fieldSetMet = getValueViaGetMet(key, obj);
                obj = ServiceAccess.execMethod(obj, fieldSetMet);
            }
        }
        return obj;
    }

    /**
     * 判断是否存在某属性的 get方法
     *
     * @param fieldSetName : 需要获取方法对应的属性
     * @param obj ： 需要获取方法对应的实例
     * @return Method 返回get方法字节码
     */
    private static Method getValueViaGetMet(String fieldSetName, Object obj) {
        fieldSetName = parGetName(fieldSetName);
        Method[] methods = ServiceAccess.reflectDeclaredMethod(obj);
        Method fieldSetMet = getGetMet(methods, fieldSetName);
        return fieldSetMet;
    }

    /**
     * 获取某属性的 get方法
     *
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    private static Method getGetMet(Method[] methods, String fieldGetMet) {
        Method tarMet = null;
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                tarMet = met;
            }
        }
        if (tarMet == null)
        {
            logger.info(String.format("Get method \"%s\" fieldGetMet failed, please check if method \"%s\" is exist.", fieldGetMet, fieldGetMet));
        }
        return tarMet;
    }

    /**
     * 拼接属性的 get方法
     *
     * @param fieldName
     * @return String
     */
    private static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "get"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    private static String isListItem(String value)
    {
        String pattern = "\\[(.*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher ma = r.matcher(value);
        boolean rs = ma.find();
        if (Pattern.matches(pattern, value))
            value = ma.group(1);
        return value;
    }

    public static Map<String, String> parseMapParam(String val)
    {
        Map<String, String> map = new HashMap<>();
        val = catchParamMap(val);
        //获取字符串中所有参数
        String[] keyArray = val.split(",");
        if (!"".equals(keyArray[0])) {
            for (String key : keyArray) {
                String[] mapKeyArray = key.split(":");
                map.put(mapKeyArray[0].trim(), mapKeyArray[1].trim());
            }
        }
        return map;
    }

    public static Map<String, List<String>> parseVerifyParam(String val)
    {
        Map<String, List<String>> map = new HashMap<>();
        if (null != val) {
            val = val.replaceAll("\\s*", "");
            //获取字符串中所有参数
            String[] keyArray = val.split("\\}\\,");
            if (!"".equals(keyArray[0])) {
                for (String key : keyArray) {
                    String[] mapKeyArray = key.split("\\:\\{");
                    String[] mapItemArray = mapKeyArray[1].replace("}", "").split("\\,");
                    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(mapItemArray));
                    map.put(mapKeyArray[0], arrayList);
                }
            }
        }
        return map;
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

    public static Object getParameterType(String parame, String parameType) throws Exception {
        String patten = "list<";
        Object sourceParame;
        Class<?> paramTypeClazz;
        if (parameType.toLowerCase().contains(patten)) {
            paramTypeClazz = ServiceAccess.reflectClazz(getListItemType(parameType));
            try {
                sourceParame = FastJsonUtil.toListBean(BufferManager.getBufferByKey(parame), paramTypeClazz);
            }
            catch (Exception ex) {
                System.out.println(ex);
                sourceParame = JAXBUtil.formXMLList(BufferManager.getBufferByKey(parame), paramTypeClazz);
            }
        }
        else {
            paramTypeClazz = ServiceAccess.reflectClazz(parameType);
            sourceParame = FastJsonUtil.toBean(BufferManager.getBufferByKey(parame), paramTypeClazz);
        }
        return sourceParame;
    }

    public static String parseJsonDate(String str) {
        String pattern ="\\\\?\\/Date\\(([\\+\\-]?[0-9]{12,15})[\\+\\-][0-9]{4}\\)\\\\?\\/";
        Pattern P = Pattern.compile(pattern);
        Matcher matcher = P.matcher(str);

        StringBuilder jsonStr = new StringBuilder();
        String tempStr = "";
        int start = 0;
        while (matcher.find(start)) {
            tempStr = str.substring(start, matcher.end());
            jsonStr.append(tempStr.replaceFirst(pattern, "$1"));
            start = matcher.end();
        }
        jsonStr.append(str.substring(start, str.length()));
        return jsonStr.toString();
    }

}

