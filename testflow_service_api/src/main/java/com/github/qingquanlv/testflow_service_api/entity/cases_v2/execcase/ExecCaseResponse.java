package com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase;

import com.github.qingquanlv.testflow_service_api.entity.Status;
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
         * caseKey
         */
        private String id;
        /**
         * parameters
         */
        private HashMap<String, String> params;
        /**
         * result
         */
        private String result;
        /**
         * status
         */
        private Status status;
}
