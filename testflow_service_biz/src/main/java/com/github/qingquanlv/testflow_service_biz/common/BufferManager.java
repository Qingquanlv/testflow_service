package com.github.qingquanlv.testflow_service_biz.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qq.lv
 */
public class BufferManager {

    final private static ThreadLocal<String> pattern = new ThreadLocal<String>();

    private static ThreadLocal<Map<String, String>> cacheMap = new ThreadLocal<Map<String, String>>();

//    public static JedisUtil jedisUtil;
//
//        /**
//     * 最大连接数
//     */
//    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));
//    /**
//     * 在jedispool中最大的idle状态(空闲的)的jedis实例的个数
//     */
//    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20"));
//    /**
//     * 在jedispool中最小的idle状态(空闲的)的jedis实例的个数
//     */
//    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","20"));
//    /**
//     * 在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
//     */
//    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));
//    /**
//     * 在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。
//     */
//    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));
//
//    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
//    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port", "20"));

    /**
     * 初始化redis连接
     *
     */
    public static void initBufferMap(String patternStr) {
        if(cacheMap.get() == null) {
            cacheMap = ThreadLocal.withInitial(() -> {
                Map<String, String> map = new HashMap<>();
                return map;
            });
            pattern.set(patternStr);
        }
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(maxTotal);
//        config.setMaxIdle(maxIdle);
//        config.setMinIdle(minIdle);
//
//        config.setTestOnBorrow(testOnBorrow);
//        config.setTestOnReturn(testOnReturn);
//        //连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
//        config.setBlockWhenExhausted(true);
//        jedisUtil = new JedisUtil(config, redisIp, redisPort, 600*1000);
//        pattern.set(patternStr);
    }

    public static void deposeBufferMap(String pattern) {
        cacheMap.remove();
//        Set<String> keys = jedisUtil.keys(String.format("%s*", pattern));
//        if (null != keys && !keys.isEmpty()) {
//            String[] string = new String[keys.size()];
//            jedisUtil.del(keys.toArray(string));
//        }
    }


    public static String getBufferByKey(String bufferKey) {
        //return jedisUtil.get(String.format("%s:%s", pattern.get(), delSpace(bufferKey)));
        return cacheMap.get().get(String.format("%s:%s", pattern.get(), delSpace(bufferKey)));
    }

    public static void addBufferByKey(String bufferKey, String bufferVal) {
        //jedisUtil.set(String.format("%s:%s", pattern.get(), delSpace(bufferKey)), bufferVal);
        cacheMap.get().put(String.format("%s:%s", pattern.get(), delSpace(bufferKey)), bufferVal);
    }

    public static void addClazzByKey(String bufferKey, String bufferVal) {
        //jedisUtil.set(String.format("%s:%s$%s", pattern.get(), "clazz", delSpace(bufferKey)), bufferVal);
        cacheMap.get().put(String.format("%s:%s$%s", pattern.get(), "clazz", delSpace(bufferKey)), bufferVal);
    }

    public static void addConfigByKey(String bufferKey, String bufferVal) {
        //jedisUtil.set(String.format("%s:%s$%s", pattern.get(), "config", delSpace(bufferKey)), bufferVal);
        cacheMap.get().put(String.format("%s:%s$%s", pattern.get(), "config", delSpace(bufferKey)), bufferVal);
    }

    public static void addStatusByKey(String bufferKey, String bufferVal) {
        //jedisUtil.set(String.format("%s:%s$%s", pattern.get(), "status", delSpace(bufferKey)), bufferVal);
        cacheMap.get().put(String.format("%s:%s$%s", pattern.get(), "status", delSpace(bufferKey)), bufferVal);
    }

    public static void appendBufferByKey(String bufferKey, String bufferVal) {
        //jedisUtil.append(String.format("%s:%s", pattern.get(), delSpace(bufferKey)), bufferVal);
        String cacheStr = getBufferByKey(bufferKey);
        cacheMap
                .get().put(
                        String.format("%s:%s$%s", pattern.get(),
                                "status",
                                delSpace(String.format("%s%s", cacheStr, bufferKey)))
                , bufferVal);
    }

    public static boolean bufferExist(String bufferObjectMapKey) {
        return false;
    }

    private static String delSpace(String bufferKey) {
        if (null != bufferKey) {
            bufferKey = bufferKey.replace(" ","");
        }
        return bufferKey;
    }
}
