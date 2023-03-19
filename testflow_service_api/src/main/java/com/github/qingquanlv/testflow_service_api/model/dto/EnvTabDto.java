package com.github.qingquanlv.testflow_service_api.model.dto;


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
public class EnvTabDto extends Model<EnvTabDto> implements Serializable {
    private static final long serialVersionUID = 612928067511889512L;
        
    private Long id;
        
    private Long appId;
        
    private String caseType;
        
    private String configKey;
        
    private String configVal;
        
    private Timestamp datachangedLasttime;
}
