package com.github.qingquanlv.testflow_service_api.entity.ops;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Config {

    private Long id;
    private String label;
    private HashMap<String, String> exec_params;
}

