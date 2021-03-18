package com.github.qingquanlv.testflow_service_api.entity.feature.queryallfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/3/9 22:19
 * @Version 1.0
 */
public class QueryAllFeatureResponse {

    /**
     * status
     */
    private Status status;
    /**
     * parameter_name
     */
    private List<QueryFeature> features;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<QueryFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<QueryFeature> features) {
        this.features = features;
    }
}
