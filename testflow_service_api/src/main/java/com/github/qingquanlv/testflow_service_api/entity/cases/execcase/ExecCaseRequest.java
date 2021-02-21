package com.github.qingquanlv.testflow_service_api.entity.cases.execcase;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 7:47
 * @Version 1.0
 */
public class ExecCaseRequest {

    /**
     * 请求唯一标识
     */
    private String requestId;
    /**
     * parameter id
     */
    private String parameter_name;
    /**
     * case type
     */
    private String case_type;
    /**
     * case id
     */
    private Long case_id;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }
}
