package com.github.qingquanlv.testflow_service_biz.utilities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.qingquanlv.testflow_service_biz.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class VerifyUtil {
    private static Logger logger = LoggerFactory.getLogger(VerifyUtil.class);
    private StringBuffer errorMsg = new StringBuffer();
    private Stack<String> index = new Stack<>();


    /**
     *
     *
     * @param operator
     * @param exp
     * @param atl
     * @return
     */
    public boolean compareValue(String operator, String exp, String atl) {
        boolean ret = false;
        switch (operator) {
            case "=": {
                ret = dataValEquals(exp, atl);
            } break;
            case "!=": {
                ret = !dataValEquals(exp, atl);
            } break;
            case ">": {
                ret = dataValGreater(exp, atl);
            } break;
            case "<": {
                ret = !dataValGreaterEquals(exp, atl);
            } break;
            case ">=": {
                ret = dataValGreaterEquals(exp, atl);
            } break;
            case "<=": {
                ret = !dataValGreater(exp, atl);
            } break;
        }
        return ret;
    }

    /**
     *
     * @param exp 预期实体
     * @param atl 实际实体
     * @param pkMap list主键
     * @param noCompareItemMap list不对比key
     */
    public String compareObj(String exp, String atl, Map<String, List<String>> pkMap, Map<String, List<String>> noCompareItemMap, Map<String, List<String>> thresholdMap) {
        try {
            Object expObj = JSON.parse(exp);
            Object atlObj = JSON.parse(atl);
            compareObj(Constants.ENTITY_ROOT, expObj, atlObj, pkMap, noCompareItemMap, thresholdMap);
        } catch (Exception ex) {
            compareObj(Constants.ENTITY_ROOT, exp, atl, pkMap, noCompareItemMap, thresholdMap);
        }
        return errorMsg.toString();
    }

    /**
     *
     * @param key 对比主键key
     * @param exp 预期实体
     * @param atl 实际实体
     * @param pkMap list主键
     * @param noCompareItemMap list不对比key
     */
    private void compareObj(String key, Object exp, Object atl, Map<String, List<String>> pkMap, Map<String, List<String>> noCompareItemMap, Map<String, List<String>> thresholdMap) {
        //判断JSONObject和JSONArray
        if (exp instanceof JSONArray && atl instanceof JSONArray) {
            compareJSONList(key, (JSONArray) exp, (JSONArray) atl, pkMap, noCompareItemMap, thresholdMap);
        } else if (exp instanceof JSONObject && atl instanceof JSONObject ) {
            index.push(key);
            compareJSONObj(key, (JSONObject) exp, (JSONObject) atl, pkMap, noCompareItemMap, thresholdMap);
            index.pop();
        } else {
            index.push(key);
            if (null != thresholdMap && thresholdMap.containsKey(key)) {
                if (!thresholdCompareVal(exp, atl, pkMap, noCompareItemMap, thresholdMap, thresholdMap.get(key))) {
                    errorMsg.append(String.format("Index: %s expected: \"%s\" not equals with actual: \"%s\".\n", index, exp, atl));
                }
            }
            else if (null != thresholdMap && thresholdMap.containsKey("all")) {
                if (!thresholdCompareVal(exp, atl, pkMap, noCompareItemMap, thresholdMap, thresholdMap.get("all"))) {
                    errorMsg.append(String.format("Index: %s expected: \"%s\" not equals with actual: \"%s\".\n", index, exp, atl));
                }
            }
            else {
                if (!compareVal(exp, atl, pkMap, noCompareItemMap, thresholdMap)) {
                    errorMsg.append(String.format("Index: %s expected: \"%s\" not equals with actual: \"%s\".\n", index, exp, atl));
                }
            }
            index.pop();
        }
    }

    /**
     * 根据主键对比两个Object
     *
     * @param expObj 预期实体
     * @param atlObj 预期实体
     * @param pkMap 主键键值对
     */
    private boolean compareJSONObj(String key, JSONObject expObj, JSONObject atlObj, Map<String, List<String>> pkMap, Map<String, List<String>> noCompareItemMap, Map<String, List<String>> thresholdMap) {
        boolean ret = true;
        Set<String> keySet = new HashSet<>();
        //取出bean里的不对比的val
        List<String> noCompareItemList = noCompareItemMap.get(key);
        keySet.addAll(expObj.keySet());
        keySet.addAll(atlObj.keySet());
        //对比Obj里面的所有val
        for (String keyItem : keySet) {
            //根据val判断，有关val不进行匹配
            if (noCompareItemList !=null && noCompareItemList.contains(keyItem)){
                continue;
            }
            compareObj(keyItem, expObj.get(keyItem), atlObj.get(keyItem), pkMap, noCompareItemMap, thresholdMap);
        }
        return ret;
    }

    /**
     * 根据主键对比两个List
     *
     * @param key 主键key
     * @param expObjList 预期实体List
     * @param atlObjList 预期实体List
     * @param pkMap 主键键值对
     */
    private void compareJSONList(String key, JSONArray expObjList, JSONArray atlObjList, Map<String, List<String>> pkMap, Map<String, List<String>> noCompareItemMap, Map<String, List<String>> thresholdMap)
    {
        JSONArray comparedAtlList = new JSONArray();
        //如果两个List长度不相等，返回error message
        if (expObjList.size() != atlObjList.size()) {
            index.push(key);
            errorMsg.append(String.format("List %s length not mathched. Expected: \"%d\", Actual \"%d\" Expected List: \"%s\", Actual List \"%s\".\n", index, expObjList.size(), atlObjList.size(), expObjList, atlObjList));
            index.pop();
        }
        List<String> pkList = pkMap.get(key);
        int k = -1;
        //循环遍历exp List
        for (Object expObjItem : expObjList) {
            int i = 0;
            int j = 0;
            k++;
            //对比list<String> 类型
            if (expObjItem instanceof String || expObjItem instanceof Integer || expObjItem instanceof Long) {
                index.push(key);
                if (!atlObjList.contains(expObjItem)) {
                    errorMsg.append(String.format("Actual entity List with index key: \"%s\" expect value \"%s\" not found.\n", index, expObjItem));
                } else {
                    atlObjList.remove(expObjItem);
                }
                index.pop();
            //没有配置list的主键则按照顺序对比
            } else if (null == pkList || pkList.size() == 0) {
                if (0 != atlObjList.size()) {
                    compareObj(String.format("%s[%s]", key, k), expObjItem, atlObjList.get(0), pkMap, noCompareItemMap, thresholdMap);
                    atlObjList.remove(atlObjList.get(0));
                }
                else {
                    errorMsg.append(String.format("Actual entity List with index key: \"%s\" expect value \"%s\" not found.\n", index, expObjItem));
                }
            } else {
                i = 0;
                j = atlObjList.size();
                //循环遍历atl List
                for (Object atlObjItem : atlObjList) {
                    //根据实体主键对比实体，如果不相等则continue
                    if (!compareObjWithSpyKey(expObjItem, atlObjItem, pkList)) {
                        i++;
                        continue;
                    }
                    compareObj(String.format("%s%s%s", key, pkList, getPKVal(expObjItem, pkList)), expObjItem, atlObjItem, pkMap, noCompareItemMap, thresholdMap);
                    comparedAtlList.add(atlObjItem);
                    //atlObjList.remove(atlObjItem);
                    break;
                }
                //如果预期值在实际值的objList中不存在
                if (j == i) {
                    errorMsg.append(String.format("Actual entity List with index key: \"%s\" expect value \"%s\" not found.\n", index, expObjItem));
                }
            }
        }
        //对比过的实体从list中删除
        atlObjList.removeAll(comparedAtlList);
        for(Object leftObj : atlObjList) {
            if (leftObj instanceof String || leftObj instanceof Integer || leftObj instanceof Long) {
                index.push(key);
                errorMsg.append(String.format("Expect entity List with index key: \"%s\" actual value \"%s\" not found.\n", index, leftObj));
                index.pop();
            }
            else if (null == pkList || pkList.size() == 0) {
                index.push(key + "[" + k + "]");
                errorMsg.append(String.format("Expect entity List with index key: \"%s\" actual value \"%s\" not found.\n", index, leftObj));
                index.pop();
            } else {
                index.push(key + pkList + getPKVal(leftObj, pkList));
                errorMsg.append(String.format("Expect entity List with index key: \"%s\" actual value \"%s\" not found.\n", index, leftObj));
                index.pop();
            }
        }
    }

    /**
     * 判断两个实体对应的属性List是否相等
     *
     * @param expObj 对比的第一个实体
     * @param atlObj 对比的第二个实体
     * @param keys 需要对比的属性List
     * @return boolean 是否相等
     */
    private boolean compareObjWithSpyKey(Object expObj, Object atlObj, List<String> keys)
    {
        boolean ret = true;
        for (String key : keys)
        {
            if (null == ((JSONObject)expObj).get(key)
                    && null == ((JSONObject)atlObj).get(key)) {
                continue;
            }
            if(null == ((JSONObject)expObj).get(key)
                    || null == ((JSONObject)atlObj).get(key)
                    || !dataValEquals(((JSONObject)expObj).get(key), ((JSONObject)atlObj).get(key)))
            {
                ret = false;
            }
        }
        return ret;
    }

    private List<Object> getPKVal(Object expObj, List<String> pkList)
    {
        List<Object> list = new ArrayList<>();
        for (String key : pkList) {
            list.add(((JSONObject) expObj).get(key));
        }
        return list;
    }

    /**
     * 阈值对比
     *
     * @param obj1
     * @param obj2
     * @param pkMap
     * @param noCompareItemMap
     * @param thresholdMap
     * @param threshold
     * @return
     */
    private boolean thresholdCompareVal(Object obj1, Object obj2, Map<String, List<String>> pkMap, Map<String, List<String>> noCompareItemMap, Map<String, List<String>> thresholdMap, List<String> threshold) {
        logger.info(String.format("%s: Start to compare object %s, Expected: \"%s\", Actual \"%s\".", new Date(), index, obj1, obj2));
        double lt = 1 - (threshold.size() > 0 ? Double.parseDouble(threshold.get(0)) : 0);
        double rt = 1 + (threshold.size() > 1 ? Double.parseDouble(threshold.get(1)) : 0);
        if (obj1 == null && "".equals(obj2)) {
            return true;
        }
        else if ("".equals(obj1) && obj2 == null) {
            return true;
        }
        else if (obj1 == null && obj2 == null) {
            return true;
        }
        else if (obj1 == null) {
            return false;
        }
        else if (obj2 == null) {
            return false;
        }
        else if (obj1 instanceof String
                && obj2 instanceof String) {
            String value1 = (String) obj1;
            String value2 = (String) obj2;
            return value1.equals(value2);
        }
        else if (obj1 instanceof Integer
                && obj2 instanceof Integer) {
            int value1 = (Integer) obj1;
            int value2 = (Integer) obj2;
            return Math.abs(value1*lt) <= Math.abs(value2) && Math.abs(value1*rt) >= Math.abs(value2);
        }
        else if (obj1 instanceof BigDecimal
                && obj2 instanceof BigDecimal) {
            double value1 = ((BigDecimal) obj1).doubleValue();
            double value2 = ((BigDecimal) obj2).doubleValue();
            return Math.abs(value1*lt) <= Math.abs(value2) && Math.abs(value1*rt) >= Math.abs(value2);
        }
        else if (obj1 instanceof Double
                && obj2 instanceof Double) {
            double value1 = (Double) obj1;
            double value2 = (Double) obj2;
            return Math.abs(value1*lt) <= Math.abs(value2) && Math.abs(value1*rt) >= Math.abs(value2);
        }
        else if (obj1 instanceof Float
                && obj2 instanceof Float) {
            float value1 = (Float) obj1;
            float value2 = (Float) obj2;
            return Math.abs(value1*lt) <= Math.abs(value2) && Math.abs(value1*rt) >= Math.abs(value2);
        }
        else if (obj1 instanceof Long
                && obj2 instanceof Long) {
            long value1 = (Long) obj1;
            long value2 = (Long) obj2;
            return Math.abs(value1*lt) <= Math.abs(value2) && Math.abs(value1*rt) >= Math.abs(value2);
        }
        else if (obj1 instanceof Boolean
                && obj2 instanceof Boolean) {
            boolean value1 = (Boolean) obj1;
            boolean value2 = (Boolean) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof BigDecimal
                && (obj2 instanceof Integer || obj2 instanceof Long)) {
            double value1 = ((BigDecimal) obj1).doubleValue();
            double value2 = Double.parseDouble(obj2.toString());
            return Math.abs(value1*lt) <= Math.abs(value2) && Math.abs(value1*rt) >= Math.abs(value2);
        }
        else if ((obj1 instanceof Integer || obj1 instanceof Long)
                && obj2 instanceof BigDecimal) {
            double value1 = Double.parseDouble(obj1.toString());
            double value2 = ((BigDecimal) obj2).doubleValue();
            return Math.abs(value1*lt) <= Math.abs(value2) && Math.abs(value1*rt) >= Math.abs(value2);
        }
        else if (obj1 instanceof Date
                && obj2 instanceof Date) {
            Date value1 = (Date) obj1;
            Date value2 = (Date) obj1;
            return value1.toString().equals(value2.toString());
        }
        else if (!obj1.getClass().equals(obj2.getClass())) {
            String value1 = obj1.toString();
            String value2 = obj2.toString();
            return value1.equals(value2);
        }
        else {
            compareObj("", obj1, obj2, pkMap, noCompareItemMap, thresholdMap);
            return true;
        }
    }

    /**
     * 对比实体
     * equals
     * @param obj1 对比实体1
     * @param obj2 对比实体2
     * @return boolean 是否相等
     */
    private boolean compareVal(Object obj1, Object obj2, Map<String, List<String>> pkMap, Map<String, List<String>> noCompareItemMap, Map<String, List<String>> thresholdMap) {
        logger.info(String.format("%s: Start to compare object %s, Expected: \"%s\", Actual \"%s\".", new Date(), index, obj1, obj2));
        if (obj1 == null && "".equals(obj2)) {
            return true;
        }
        else if ("".equals(obj1) && obj2 == null) {
            return true;
        }
        else if (obj1 == null && obj2 == null) {
            return true;
        }
        else if (obj1 == null) {
            return false;
        }
        else if (obj2 == null) {
            return false;
        }

        else if (obj1 instanceof Integer
                && obj2 instanceof Integer) {
            int value1 = (Integer) obj1;
            int value2 = (Integer) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof BigDecimal
                && obj2 instanceof BigDecimal) {
            double value1 = ((BigDecimal) obj1).doubleValue();
            double value2 = ((BigDecimal) obj2).doubleValue();
            return value1 == value2;
        }
        else if (obj1 instanceof String
                && obj2 instanceof String) {
            String value1 = (String) obj1;
            String value2 = (String) obj2;
            return value1.equals(value2);
        }
        else if (obj1 instanceof Double
                && obj2 instanceof Double) {
            double value1 = (Double) obj1;
            double value2 = (Double) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof Float
                && obj2 instanceof Float) {
            float value1 = (Float) obj1;
            float value2 = (Float) obj2;
            return value1 == value2;
        } else if (obj1 instanceof Long
                && obj2 instanceof Long) {
            long value1 = (Long) obj1;
            long value2 = (Long) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof Boolean
                && obj2 instanceof Boolean) {
            boolean value1 = (Boolean) obj1;
            boolean value2 = (Boolean) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof Date
                && obj2 instanceof Date) {
            Date value1 = (Date) obj1;
            Date value2 = (Date) obj1;
            return value1.toString().equals(value2.toString());
        }
        else if ((obj1 instanceof Integer || obj1 instanceof Long)
                && obj2 instanceof BigDecimal) {
            double value1 = Double.parseDouble(obj1.toString());
            double value2 = ((BigDecimal) obj2).doubleValue();
            return value1 == value2;
        }
        else if (obj1 instanceof BigDecimal
                && (obj2 instanceof Integer || obj2 instanceof Long)) {
            double value1 = ((BigDecimal) obj1).doubleValue();
            double value2 = Double.parseDouble(obj2.toString());
            return value1 == value2;
        }
        else if (!obj1.getClass().equals(obj2.getClass())) {
            String value1 = obj1.toString();
            String value2 = obj2.toString();
            return value1.equals(value2);
        }
        else {
            compareObj("", obj1, obj2, pkMap, noCompareItemMap, thresholdMap);
            return true;
        }
    }

    /**
     * 对比Value
     *
     * @param obj1
     * @param obj2
     * @return
     */
    private static boolean dataValEquals(Object obj1, Object obj2) {
        if (obj1 == null && "".equals(obj2)) {
            return true;
        }
        else if ("".equals(obj1) && obj2 == null) {
            return true;
        }
        else if (obj1 == null && obj2 == null) {
            return true;
        }
        else if (obj1 == null) {
            return false;
        }
        else if (obj2 == null) {
            return false;
        }
        else if (obj1 instanceof Integer
                && obj2 instanceof Integer) {
            int value1 = (Integer) obj1;
            int value2 = (Integer) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof String
                && obj2 instanceof String) {
            String value1 = (String) obj1;
            String value2 = (String) obj2;
            return value1.equals(value2);
        }
        else if (obj1 instanceof Double
                && obj2 instanceof Double) {
            double value1 = (Double) obj1;
            double value2 = (Double) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof Float
                && obj2 instanceof Float) {
            float value1 = (Float) obj1;
            float value2 = (Float) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof Long
                && obj2 instanceof Long) {
            long value1 = (Long) obj1;
            long value2 = (Long) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof Boolean
                && obj2 instanceof Boolean) {
            boolean value1 = (Boolean) obj1;
            boolean value2 = (Boolean) obj2;
            return value1 == value2;
        }
        else if (obj1 instanceof Date
                && obj2 instanceof Date) {
            Date value1 = (Date) obj1;
            Date value2 = (Date) obj1;
            return value1.toString().equals(value2.toString());
        }
        else if ((obj1 instanceof Integer || obj1 instanceof Long)
                && obj2 instanceof BigDecimal) {
            double value1 = Double.parseDouble(obj1.toString());
            double value2 = ((BigDecimal) obj2).doubleValue();
            return value1 == value2;
        }
        else if (obj1 instanceof BigDecimal
                && (obj2 instanceof Integer || obj2 instanceof Long)) {
            double value1 = ((BigDecimal) obj1).doubleValue();
            double value2 = Double.parseDouble(obj2.toString());
            return value1 == value2;
        }
        else if (!obj1.getClass().equals(obj2.getClass())) {
            String value1 = obj1.toString();
            String value2 = obj2.toString();
            return value1.equals(value2);
        }
        else {
            return false;
        }
    }

    /**
     * 对比Value
     *
     * @param obj1
     * @param obj2
     * @return
     */
    private static boolean dataValGreater(Object obj1, Object obj2) {
        if (obj1 == null && "".equals(obj2)) {
            return false;
        }
        else if ("".equals(obj1) && obj2 == null) {
            return false;
        }
        else if (obj1 == null && obj2 == null) {
            return false;
        }
        else if (obj1 == null) {
            return false;
        }
        else if (obj2 == null) {
            return false;
        }
        else if (obj1 instanceof Integer
                && obj2 instanceof Integer) {
            int value1 = (Integer) obj1;
            int value2 = (Integer) obj2;
            return value1 > value2;
        }
        else if (obj1 instanceof Double
                && obj2 instanceof Double) {
            double value1 = (Double) obj1;
            double value2 = (Double) obj2;
            return value1 > value2;
        }
        else if (obj1 instanceof Float
                && obj2 instanceof Float) {
            float value1 = (Float) obj1;
            float value2 = (Float) obj2;
            return value1 > value2;
        }
        else if (obj1 instanceof Long
                && obj2 instanceof Long) {
            long value1 = (Long) obj1;
            long value2 = (Long) obj2;
            return value1 > value2;
        }
        else if (obj1 instanceof Date
                && obj2 instanceof Date) {
            Date value1 = (Date) obj1;
            Date value2 = (Date) obj1;
            return value1.after(value2);
        }
        else if ((obj1 instanceof Integer || obj1 instanceof Long)
                && obj2 instanceof BigDecimal) {
            double value1 = Double.parseDouble(obj1.toString());
            double value2 = ((BigDecimal) obj2).doubleValue();
            return value1 > value2;
        }
        else if (obj1 instanceof BigDecimal
                && (obj2 instanceof Integer || obj2 instanceof Long)) {
            double value1 = ((BigDecimal) obj1).doubleValue();
            double value2 = Double.parseDouble(obj2.toString());
            return value1 > value2;
        }
        else {
            return false;
        }
    }

    /**
     * 对比Value
     *
     * @param obj1
     * @param obj2
     * @return
     */
    private static boolean dataValGreaterEquals(Object obj1, Object obj2) {
        if (obj1 == null && "".equals(obj2)) {
            return true;
        }
        else if ("".equals(obj1) && obj2 == null) {
            return true;
        }
        else if (obj1 == null && obj2 == null) {
            return true;
        }
        else if (obj1 == null) {
            return false;
        }
        else if (obj2 == null) {
            return false;
        }
        else if (obj1 instanceof Integer
                && obj2 instanceof Integer) {
            int value1 = (Integer) obj1;
            int value2 = (Integer) obj2;
            return value1 >= value2;
        }
        else if (obj1 instanceof Double
                && obj2 instanceof Double) {
            double value1 = (Double) obj1;
            double value2 = (Double) obj2;
            return value1 >= value2;
        }
        else if (obj1 instanceof Float
                && obj2 instanceof Float) {
            float value1 = (Float) obj1;
            float value2 = (Float) obj2;
            return value1 >= value2;
        }
        else if (obj1 instanceof Long
                && obj2 instanceof Long) {
            long value1 = (Long) obj1;
            long value2 = (Long) obj2;
            return value1 >= value2;
        }
        else if (obj1 instanceof Date
                && obj2 instanceof Date) {
            Date value1 = (Date) obj1;
            Date value2 = (Date) obj1;
            return value1.toString().equals(value2.toString())
                    || value1.after(value2);
        }
        else if ((obj1 instanceof Integer || obj1 instanceof Long)
                && obj2 instanceof BigDecimal) {
            double value1 = Double.parseDouble(obj1.toString());
            double value2 = ((BigDecimal) obj2).doubleValue();
            return value1 >= value2;
        }
        else if (obj1 instanceof BigDecimal
                && (obj2 instanceof Integer || obj2 instanceof Long)) {
            double value1 = ((BigDecimal) obj1).doubleValue();
            double value2 = Double.parseDouble(obj2.toString());
            return value1 >= value2;
        }
        else {
            return false;
        }
    }
}
