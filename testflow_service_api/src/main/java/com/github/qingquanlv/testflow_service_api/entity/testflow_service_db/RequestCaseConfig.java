package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/13 19:28
 */
@Data
@Builder
public class RequestCaseConfig {

        private static final long serialVersionUID = 1L;

        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        private Long caseId;

        private String url;

        private String requestBody;

        private String requestType;

        private String contentType;

        private String requestConfigs;

        private String requestHeaders;

        private Timestamp datachangedLasttime;
}
