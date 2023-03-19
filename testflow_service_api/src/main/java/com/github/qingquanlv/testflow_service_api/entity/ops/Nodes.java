package com.github.qingquanlv.testflow_service_api.entity.ops;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Nodes {

    private Long id;
    private Long x;
    private Long y;
    private String label;
    private String clazz;
}

