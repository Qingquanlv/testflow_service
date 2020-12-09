package com.github.qingquanlv.testflow_service_api.entity;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/9 7:50
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

