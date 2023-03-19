package com.github.qingquanlv.testflow_service_api.entity.feature.queryallfeature;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class QueryFeature {

    /**
     * feature Name
     */
    private Long feature_id;
    /**
     * feature Name
     */
    private String featureName;
    /**
     * feature Name 介绍
     */
    private String description;

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

    public Long getFeature_id() {
        return feature_id;
    }

    public void setFeature_id(Long feature_id) {
        this.feature_id = feature_id;
    }
}
