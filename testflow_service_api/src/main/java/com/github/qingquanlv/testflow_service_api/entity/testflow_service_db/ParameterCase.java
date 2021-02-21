package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 7:19
 * @Version 1.0
 */
public class ParameterCase {

    //id
    private Long id;
    //parameter_id
    private String parameter_name;
    //parameter_key
    private String parameter_key;
    //parameter_value
    private String parameter_value;
    //时间戳
    private Timestamp datachanged_lasttime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameter_key() {
        return parameter_key;
    }

    public void setParameter_key(String parameter_key) {
        this.parameter_key = parameter_key;
    }

    public String getParameter_value() {
        return parameter_value;
    }

    public void setParameter_value(String parameter_value) {
        this.parameter_value = parameter_value;
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
