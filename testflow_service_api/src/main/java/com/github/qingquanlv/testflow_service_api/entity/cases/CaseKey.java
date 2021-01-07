package com.github.qingquanlv.testflow_service_api.entity.cases;

import com.github.qingquanlv.testflow_service_api.entity.CaseTypeEnum;

/**
 * @Author Qingquan Lv
 * @Date 2020/11/29 10:57
 * @Version 1.0
 */
public class CaseKey {

    //case id
    private Integer caseId;
    //case name
    private String caseName;
    //case type
    private String caseType;
    //featureId
    private Integer featureId;

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
}
