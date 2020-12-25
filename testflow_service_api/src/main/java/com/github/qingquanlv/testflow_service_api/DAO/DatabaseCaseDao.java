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

/**
 * @author qingquanlv
 */
@Repository
public class DatabaseCaseDao {

    public final String resource = "mybatis-config.xml";

    /**
     * 添加data case到database
     *
     * @param case_name
     * @param sql
     * @return
     * @throws Exception
     */
    public StepBuffer addDataTestCase(String case_name, String sql) throws Exception{
        Map<String, String> map= new HashMap();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        StepBuffer stepBuffer = session.selectOne("select", map);
        session.close();
        return stepBuffer;
    }
}
