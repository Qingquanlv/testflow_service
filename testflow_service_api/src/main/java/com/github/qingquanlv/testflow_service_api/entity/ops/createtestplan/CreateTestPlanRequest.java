package com.github.qingquanlv.testflow_service_api.entity.ops.createtestplan;

import com.github.qingquanlv.testflow_service_api.entity.ops.Config;
import com.github.qingquanlv.testflow_service_api.entity.ops.Edges;
import com.github.qingquanlv.testflow_service_api.entity.ops.Nodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestPlanRequest {

    private String requestId;
    private Long testPlanId;
    private String testPlanName;
    private String description;
    private List<Nodes> nodes;
    private List<Edges> edges;
    private List<Config> config;
}


