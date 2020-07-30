package com.github.qingquanlv.testflow_service_biz.stepdefinations;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Buffer {

    public final String resource = "mybatis-config.xml";

    /**
     * 根据BufferKey查询缓存
     * @param bufferKey
     * @return
     * @throws Exception
     */
    public String queryStepBuffer(String bufferKey) throws Exception{
        Map<String, String> map= new HashMap();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        String stepBuffer = session.selectOne("select", map);
        session.close();
        return stepBuffer;
    }

    /**
     * 根据BufferKey缓存
     * @param bufferKey
     * @return
     * @throws Exception
     */
    public void addStepBuffer(String bufferKey, String buffer) throws Exception{
        Map<String, String>  map= new HashMap();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        session.insert("select", map);
        session.close();
    }
}
