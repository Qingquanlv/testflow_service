package com.github.qingquanlv.testflow_service_api.entity.job.jobresult;

import com.github.qingquanlv.testflow_service_api.entity.feature_v2.CaseInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResults {

        private Timestamp starttime;

        private Timestamp endtime;

        private List<CaseInfo> info;
}
