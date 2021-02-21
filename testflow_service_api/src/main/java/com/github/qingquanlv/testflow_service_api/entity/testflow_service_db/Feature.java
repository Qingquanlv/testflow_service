package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 11:13
 * @Version 1.0
 */
public class Feature {

    //case_id
    private Long feature_id;
    //name
    private String feature_name;
    //parameter_name
    private String parameter_name;
    //时间戳
    private Timestamp datachanged_lasttime;


    public Long getFeature_id() {
        return feature_id;
    }

    public void setFeature_id(Long feature_id) {
        this.feature_id = feature_id;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public Timestamp getDatachanged_lasttime() {
        return datachanged_lasttime;
    }

    public void setDatachanged_lasttime(Timestamp datachanged_lasttime) {
        this.datachanged_lasttime = datachanged_lasttime;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }
}
