package com.github.qingquanlv.testflow_service_api.entity.cases_v2;

import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Params;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/10 21:40
 */
@Data
@Builder
public class Config {

        private String id;
        private String clazz;
        private List<Params> params;
}
