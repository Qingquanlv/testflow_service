package com.github.qingquanlv.testflow_service_api.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("env_tab")
public class EnvTabPo extends Model<EnvTabPo> implements Serializable {
    private static final long serialVersionUID = 667380630113202483L;
    
            private Long id;
    
            private Long appId;
    
            private String caseType;
    
            private String configKey;
    
            private String configVal;
    
            private Timestamp datachangedLasttime;

}
