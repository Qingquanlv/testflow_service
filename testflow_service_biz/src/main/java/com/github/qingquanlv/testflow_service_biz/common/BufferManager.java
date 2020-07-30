package com.github.qingquanlv.testflow_service_biz.common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class BufferManager {

    public static final String resource = "mybatis-config.xml";

    public static void initBufferMap() { }

    public static void deposeBufferMap() {
    }

    public static String getBufferByKey(String bufferKey) throws Exception {
        Map<String, String> map= new HashMap();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        String stepBuffer = session.selectOne("select", map);
        session.close();
        return stepBuffer;
    }

    public static void addBufferByKey(String bufferKey, String bufferVal) throws Exception {
        Map<String, String>  map= new HashMap();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        session.insert("select", map);
        session.close();
    }

    public static boolean bufferExist(String bufferObjectMapKey) {
        return false;
    }
}
