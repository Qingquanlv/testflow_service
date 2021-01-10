package com.github.qingquanlv.testflow_service_api.entity.feature.updatefeature;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.DataBaseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.parse.ParseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.request.RequestCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.verification.VerificationCases;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/7 23:46
 * @Version 1.0
 */
public class UpdateFeatureRequest {
    /**
     * 请求唯一标识
     */
    private String requestId;
    /**
     * feature Name
     */
    private String featureName;
    /**
     * feature Name 介绍
     */
    private String description;
    /**
     * data cases
     */
    private List<DataBaseCases> dataBaseCasesList;
    /**
     * parse cases
     */
    private List<ParseCases> paserCasesList;
    /**
     * request cases
     */
    private List<RequestCases> requestCasesList;
    /**
     * verification cases
     */
    private List<VerificationCases> verificationCasesList;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DataBaseCases> getDataBaseCasesList() {
        return dataBaseCasesList;
    }

    public void setDataBaseCasesList(List<DataBaseCases> dataBaseCasesList) {
        this.dataBaseCasesList = dataBaseCasesList;
    }

    public List<RequestCases> getRequestCasesList() {
        return requestCasesList;
    }

    public void setRequestCasesList(List<RequestCases> requestCasesList) {
        this.requestCasesList = requestCasesList;
    }

    public List<VerificationCases> getVerificationCasesList() {
        return verificationCasesList;
    }

    public void setVerificationCasesList(List<VerificationCases> verificationCasesList) {
        this.verificationCasesList = verificationCasesList;
    }

    public List<ParseCases> getPaserCasesList() {
        return paserCasesList;
    }

    public void setPaserCasesList(List<ParseCases> paserCasesList) {
        this.paserCasesList = paserCasesList;
    }
}
