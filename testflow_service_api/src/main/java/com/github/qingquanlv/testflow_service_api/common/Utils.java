package com.github.qingquanlv.testflow_service_api.common;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/7 7:53
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
        if (null != key) {
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
}
