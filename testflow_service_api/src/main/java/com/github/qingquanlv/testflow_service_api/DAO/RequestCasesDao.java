package com.github.qingquanlv.testflow_service_api.DAO;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.StepBuffer;
import com.github.qingquanlv.testflow_service_api.entity.cases.request.RequestCasesRequest;
import com.github.qingquanlv.testflow_service_api.entity.savefeature.SaveFeatureRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qingquanlv
 */
@Repository
public class RequestCasesDao {

        public final String resource = "mybatis-config.xml";
        List<SaveFeatureRequest> selectUserByTime(@Param(value="dateVO")DateVO dateVO);


        /**
         *  查询Features
         * @param bufferKey
         * @return
         * @throws Exception
         */
        public StepBuffer selectRequestCases(String bufferKey) throws Exception{
                Map<String, String> map= new HashMap();
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
         * @return
         * @throws Exception
         */
        public void addRequestCases(String bufferKey) throws Exception{
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SqlSession session = sqlSessionFactory.openSession();
                RequestCases userMapper = session.getMapper(RequestCases.class);
                RequestCasesRequest user = new RequestCasesRequest();
                user.setUrl("abc");
                int rows = userMapper.inserRequestCases(user);
                System.out.println(rows);
                // 一定要提交
                session.commit();
                session.close();
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
