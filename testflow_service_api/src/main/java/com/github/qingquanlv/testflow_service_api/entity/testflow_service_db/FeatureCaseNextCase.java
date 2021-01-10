package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/7 22:37
 * @Version 1.0
 */
public class FeatureCaseNextCase {
    //id
    private Long id;
    //feature_id
    private Long feature_case_id;
    //case_id
    private Long case_id;
    //case_type
    private String case_type;
    //时间戳
    private Timestamp datachanged_lasttime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDatachanged_lasttime() {
        return datachanged_lasttime;
    }

    public void setDatachanged_lasttime(Timestamp datachanged_lasttime) {
        this.datachanged_lasttime = datachanged_lasttime;
    }

    public String getCase_type() {
        return case_type;
    }

    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }

    public Long getCase_id() {
        return case_id;
    }

    public void setCase_id(Long case_id) {
        this.case_id = case_id;
    }

    public Long getFeature_case_id() {
        return feature_case_id;
    }

    public void setFeature_case_id(Long feature_case_id) {
        this.feature_case_id = feature_case_id;
    }
}
