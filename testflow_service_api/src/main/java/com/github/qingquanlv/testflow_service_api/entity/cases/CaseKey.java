package com.github.qingquanlv.testflow_service_api.entity.cases;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class CaseKey {

    //case id
    private Long caseId;
    //case name
    private String caseName;
    //case type
    private String caseType;

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

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
}
