package com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.CaseInfo;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/14 17:41
 */
@Data
@Builder
public class ExecCaseResponse {

        /**
         * parameters
         */
        private HashMap<String, String> params;
        /**
         * result
         */
        private CaseInfo info;
        /**
         * status
         */
        private Status status;
}
