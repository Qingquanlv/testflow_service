package com.github.qingquanlv.testflow_service_biz.stepdefinations;

import com.github.qingquanlv.testflow_service_biz.common.BufferManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import com.github.qingquanlv.testflow_service_biz.utilities.ParamUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Database {
    public final String resource = "mybatis-config.xml";


    /**
     * 根据Map查询DB
     *
     * @param sql mybatis parame
     * @return 查询结果序列化Json
     * @throws Exception
     */
    public String queryDataBase(String caseName, String sql) throws Exception{
        sql = ParamUtil.parseParam(sql);
        BufferManager.addConfigByKey(caseName,
                String.format("sql:%s",
                        sql));
        System.out.print("Start To Load Session");
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        System.out.print("Session is" + session);
        List<LinkedHashMap<String, Object>> value = session.selectList("select", sql);
        session.close();
        return FastJsonUtil.toJson(value);
    }

    /**
     * 根据Map查询DB
     *
     * @param queryKey mybatis key
     * @param param mybatis parame
     * @return 查询结果序列化Json
     * @throws Exception
     */
    public String queryDataBase(String caseName, String queryKey, String param) throws Exception{
        param = ParamUtil.parseParam(param);
        Map<String, String> map = ParamUtil.parseMapParam(param);
        BufferManager.addConfigByKey(caseName,
                String.format("sql:%s",
                        param));
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        Object blog = session.selectList(queryKey, map);
        session.close();
        String str = FastJsonUtil.toJson(blog);
        return str;
    }
}