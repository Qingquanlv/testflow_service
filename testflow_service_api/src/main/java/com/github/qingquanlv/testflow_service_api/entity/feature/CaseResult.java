package com.github.qingquanlv.testflow_service_api.entity.feature;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class CaseResult {

    /**
     * case type
     */
    private String case_type;
    /**
     * case id
     */
    private Long case_id;
    /**
     * result
     */
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
