package com.github.qingquanlv.testflow_service_api.entity;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public enum  CaseTypeEnum {

    DATABASE(0),

    PARSE(1),

    REQUEST(2),

    VERIFICATION(3);

    private int index;

    CaseTypeEnum(int idx) {
        this.index = idx;
    }

    public int getIndex() {
        return index;
    }
}

