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
public class Edges {
    private String source;
    private String target;
    private long sourceAnchor;
    private long targetAnchor;

}

