package com.github.qingquanlv.testflow_service_api.entity.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parameters {

        /**
         * parameter_key
         */
        private String parameterIndexName;

        private List<HashMap<String, String>> parameters;
}
