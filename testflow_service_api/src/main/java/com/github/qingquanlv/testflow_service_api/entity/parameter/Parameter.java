package com.github.qingquanlv.testflow_service_api.entity.parameter;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/10 11:15
 * @Version 1.0
 */
public class Parameter {

    /**
     * parameter_key
     */
    private String parameter_key;
    /**
     * parameter_value
     */
    private String parameter_value;

    public String getParameter_value() {
        return parameter_value;
    }

    public void setParameter_value(String parameter_value) {
        this.parameter_value = parameter_value;
    }

    public String getParameter_key() {
        return parameter_key;
    }

    public void setParameter_key(String parameter_key) {
        this.parameter_key = parameter_key;
    }
}
