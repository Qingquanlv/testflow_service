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
    //case type
    private CaseTypeEnum caseType;
    //featureId
    private Integer featureId;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public CaseTypeEnum getCaseEnum() {
        return caseType;
    }

    public void setCaseEnum(CaseTypeEnum caseEnum) {
        this.caseType = caseEnum;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }
}
