package com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.CaseInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecFeatureResponse {

        private Status status;
        private HashMap<String, String> parameters;
        private List<CaseInfo> info;
}
