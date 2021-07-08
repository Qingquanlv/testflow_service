package com.github.qingquanlv.testflow_service_api.entity.job.jobresult;

import com.github.qingquanlv.testflow_service_api.entity.feature_v2.CaseInfo;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/7/7 22:57
 */
@Data
@Builder
public class TaskResults {

        private Timestamp starttime;

        private Timestamp endtime;

        private List<CaseInfo> info;
}
