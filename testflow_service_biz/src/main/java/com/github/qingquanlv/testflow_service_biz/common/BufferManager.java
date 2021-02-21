package com.github.qingquanlv.testflow_service_biz.common;

import com.github.qingquanlv.testflow_service_biz.utilities.JedisUtil;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class BufferManager {

    public static JedisUtil jedisUtil;
    public static String pattern;

    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")); //最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20"));//在jedispool中最大的idle状态(空闲的)的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","20"));//在jedispool中最小的idle状态(空闲的)的jedis实例的个数

    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port", "20"));

    /**
     * 初始化redis连接
     *
     */
    public static void initBufferMap() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。

        jedisUtil = new JedisUtil(config, redisIp, redisPort);

        pattern = "";
    }

    /**
     * 初始化redis连接
     *
     */
    public static void initBufferMap(String patternStr) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。

        jedisUtil = new JedisUtil(config, redisIp, redisPort);

        pattern = patternStr;
    }

    public static void deposeBufferMap(String pattern) {
        Set<String> keys = jedisUtil.keys(String.format("%s*", pattern));
        if (null != keys && !keys.isEmpty()) {
            String[] string = new String[keys.size()];
            jedisUtil.del(keys.toArray(string));
        }
    }

    public static String getBufferByKey(String bufferKey) throws Exception {
        return jedisUtil.get(String.format("%s:%s", pattern, bufferKey));
    }

    public static void addBufferByKey(String bufferKey, String bufferVal) throws Exception {
        jedisUtil.set(String.format("%s:%s", pattern, bufferKey), bufferVal);

    }

    public static boolean bufferExist(String bufferObjectMapKey) {
        return false;
    }
}
