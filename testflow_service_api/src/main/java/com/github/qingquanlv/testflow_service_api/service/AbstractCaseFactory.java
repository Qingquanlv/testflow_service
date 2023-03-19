package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/7 11:51
 */
public abstract class AbstractCaseFactory {

    public abstract BasicCase reflactCaze(String caseName);
    public abstract BasicCase reflactCaze(String caseName, Object obj);
    //public abstract IExecCase getExecute(String n);
}

