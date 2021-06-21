package com.github.qingquanlv.testflow_service_api.entity.cases_v2;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/10 21:39
 */
@Data
@Builder
public class Params {
        private String key;
        private String value;
}
