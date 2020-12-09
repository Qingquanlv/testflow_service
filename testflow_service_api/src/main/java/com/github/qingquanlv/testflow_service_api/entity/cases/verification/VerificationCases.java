package com.github.qingquanlv.testflow_service_api.entity.cases.verification;

import com.github.qingquanlv.testflow_service_api.entity.cases.BaseCase;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2020/11/29 10:57
 * @Version 1.0
 */
public class VerificationCases extends BaseCase {
    //name
    private String name;
    //verify type
    private String verifyType;
    //verify Parameter
    private List<String> parameters;

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
