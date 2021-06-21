package com.github.qingquanlv.testflow_service_api.entity.feature_v2;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/10 21:40
 */
@Data
@Builder
public class Config {

        private String label;
        private HashMap<String, String> params;
}
