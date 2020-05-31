package com.github.qingquanlv.testflow_service.DAO;

import com.github.qingquanlv.testflow_service.entity.cases.database.StepBuffer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StepBufferDao {
    public final String resource = "mybatis-config.xml";

    /**
     * 根据BufferKey查询缓存
     * @param bufferKey
     * @return
     * @throws Exception
     */
    public StepBuffer queryStepBuffer(String bufferKey, String buffer) throws Exception{
        Map<String, String>  map= new HashMap();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        StepBuffer stepBuffer = session.selectOne("select", map);
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
