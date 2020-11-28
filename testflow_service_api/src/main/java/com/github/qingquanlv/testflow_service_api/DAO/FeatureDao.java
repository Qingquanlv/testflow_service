package com.github.qingquanlv.testflow_service_api.DAO;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.StepBuffer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FeatureDao {

        public final String resource = "mybatis-config.xml";

        /**
         *  添加Features
         * @param bufferKey
         * @param buffer
         * @return
         * @throws Exception
         */
        public StepBuffer addTestFeatures(String bufferKey, String buffer) throws Exception{
                Map<String, String> map= new HashMap();
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession session = sqlSessionFactory.openSession();
                StepBuffer stepBuffer = session.selectOne("select", map);
                session.close();
                return stepBuffer;
        }

        /**
         *  更新Features
         * @param bufferKey
         * @param buffer
         * @return
         * @throws Exception
         */
        public StepBuffer updateTestFeatures(String bufferKey, String buffer) throws Exception{
                Map<String, String>  map= new HashMap();
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession session = sqlSessionFactory.openSession();
                StepBuffer stepBuffer = session.selectOne("select", map);
                session.close();
                return stepBuffer;
        }

        /**
         *  删除Features
         * @param bufferKey
         * @param buffer
         * @return
         * @throws Exception
         */
        public StepBuffer deleteTestFeatures(String bufferKey, String buffer) throws Exception{
                Map<String, String>  map= new HashMap();
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession session = sqlSessionFactory.openSession();
                StepBuffer stepBuffer = session.selectOne("select", map);
                session.close();
                return stepBuffer;
        }

        /**
         *  添加Features
         * @param bufferKey
         * @param buffer
         * @return
         * @throws Exception
         */
        public StepBuffer addRequestCases(String bufferKey, String buffer) throws Exception{
                Map<String, String> map= new HashMap();
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession session = sqlSessionFactory.openSession();
                StepBuffer stepBuffer = session.selectOne("select", map);
                session.close();
                return stepBuffer;
        }

        /**
         *  更新Features
         * @param bufferKey
         * @param buffer
         * @return
         * @throws Exception
         */
        public StepBuffer updateRequestCases(String bufferKey, String buffer) throws Exception{
                Map<String, String>  map= new HashMap();
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession session = sqlSessionFactory.openSession();
                StepBuffer stepBuffer = session.selectOne("select", map);
                session.close();
                return stepBuffer;
        }

        /**
         *  删除Features
         * @param bufferKey
         * @param buffer
         * @return
         * @throws Exception
         */
        public StepBuffer deleteRequestCases(String bufferKey, String buffer) throws Exception{
                Map<String, String>  map= new HashMap();
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession session = sqlSessionFactory.openSession();
                StepBuffer stepBuffer = session.selectOne("select", map);
                session.close();
                return stepBuffer;
        }
}
