package com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.CaseInfo;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/22 20:14
 */
@Data
@Builder
public class ExecFeatureResponse {

        private Status status;
        private HashMap<String, String> parameters;
        private List<CaseInfo> info;
}
