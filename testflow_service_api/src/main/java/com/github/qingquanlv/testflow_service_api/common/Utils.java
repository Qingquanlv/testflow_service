package com.github.qingquanlv.testflow_service_api.common;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class Utils {

    public static List<String> toListStr(String key) {
        List<String> list = new ArrayList<>();
        if (null != key) {
            String[] arrays = key.split(",");
            list = Stream.of(arrays).collect(Collectors.toList());
        }
        return list;
    }

    public static List<Long> toListLong(String key) {
        List<Long> list = new ArrayList<>();
        if (null != key && !"".equals(key) ) {
            String[] arrays = key.split(",");
            list = Arrays.stream(arrays)
                    .map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
        }
        return list;
    }

    public static String listToStr(List<String> list) {
        String str = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (String item : list) {
                if (!StringUtils.isEmpty(str)) {
                    str = String.format("%s,%s", str, item);
                }
                else {
                    str = item;
                }
            }
         }
        return str;
    }

    public static String shortHashCode(String str) {
        int strHashCode = str.hashCode();
        Short tStr = (short) (strHashCode ^ (strHashCode >>> 16));
        return tStr.toString();
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getCaseNameByType(String type) {
        switch (type) {
            case "request":
                return "RequestCaze";
            case "database":
                return "DatabaseCase";
            case "tidb":
                return "TidbCase";
            case "druid":
                return "DruidCase";
            case "parse":
                return "ParseCase";
            case "compare_val":
                return "ComparevalCase";
            case "compare_path":
                return "ComparepathCase";
            case "compare_obj":
                return "CompareobjCase";
            case "feature":
                return "RequestCaze";
            case "condition_if":
                return "IfConditionCase";
            case "condition_for":
                return "ForConditionCase";
            default:
                return "RequestCase";
        }
    }

    public static <T> List<Map<String, String>> listToListMap (List<T> objs) {
        List<Map<String, String>> params =new ArrayList<>();
        for (T o : objs) {
            Map<String, String> map = new HashMap<>();
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID")
                    ||field.getName().equals("id")
                    || field.getName().equals("datachanged_lasttime")) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    map.put(field.getName(),field.get(o).toString());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            params.add(map);
        }
        return params;
    }

    public static Map<String, String> ObjToMap(Object obj) {
        Map<String, String> params = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id")
                || field.getName().equals("caseId")
                || field.getName().equals("datachanged_lasttime")) {
                continue;
            }
            try {
                field.setAccessible(true);
                params.put(field.getName(),field.get(obj).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }
}
