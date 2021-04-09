package com.github.qingquanlv.testflow_service_api.entity.feature.execfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.feature.CaseResult;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/5 8:41
 * @Version 1.0
 */
public class ExecFeatureResponse {
    /**
     * status
     */
    private Status status;
    /**
     * info
     */
    private String info;
    /**
     * assertion
     */
    private String assertion;
    /**
     * parameters
     */
    private List<Parameter> parameters;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAssertion() {
        return assertion;
    }

    public void setAssertion(String assertion) {
        this.assertion = assertion;
    }
}
