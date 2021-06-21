package com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase;

import com.github.qingquanlv.testflow_service_api.entity.cases.CaseKey;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/14 17:41
 */
@Data
@Builder
public class StepResult {

        private CaseKey caseKey;

        private String info;

        private String asseration;
}
