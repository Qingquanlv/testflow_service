package com.github.qingquanlv.testflow_service.DAO;

import com.github.qingquanlv.testflow_service.entity.database.StepBuffer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class StepBufferDao {
    public final String resource = "mybatis-config.xml";

    /**
     * 根据BufferKey查询缓存
     * @param bufferKey
     * @return
     * @throws Exception
     */
    public StepBuffer queryStepBuffer(String bufferKey) throws Exception{
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        StepBuffer stepBuffer = session.select("select", bufferKey);
        session.close();
        return stepBuffer;
    }

    /**
     * 根据BufferKey缓存
     * @param bufferKey
     * @return
     * @throws Exception
     */
    public void addStepBuffer(String bufferKey) throws Exception{
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        StepBuffer stepBuffer = session.select("select", bufferKey);
        session.close();
    }
}
