package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 11:12
 * @Version 1.0
 */
public class PaserCase {

    //case_id
    private Long case_id;
    //case_type
    private String case_name;
    //parameters
    private String parameters;
    //cvt_method_source
    private String cvt_method_source;
    //return_type
    private String return_type;
    //时间戳
    private Timestamp datachanged_lasttime;

    public Long getCase_id() {
        return case_id;
    }

    public void setCase_id(Long case_id) {
        this.case_id = case_id;
    }

    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getCvt_method_source() {
        return cvt_method_source;
    }

    public void setCvt_method_source(String cvt_method_source) {
        this.cvt_method_source = cvt_method_source;
    }

    public String getReturn_type() {
        return return_type;
    }

    public void setReturn_type(String return_type) {
        this.return_type = return_type;
    }

    public Timestamp getDatachanged_lasttime() {
        return datachanged_lasttime;
    }

    public void setDatachanged_lasttime(Timestamp datachanged_lasttime) {
        this.datachanged_lasttime = datachanged_lasttime;
    }
}
