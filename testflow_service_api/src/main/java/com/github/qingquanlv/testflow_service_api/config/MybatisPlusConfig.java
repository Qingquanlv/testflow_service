package com.github.qingquanlv.testflow_service_api.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: qingquan.lv
 */
@Configuration
public class MybatisPlusConfig {
        /**
         *   mybatis-plus分页插件
         */
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
                MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
                interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
                return interceptor;
        }

        @Bean
        public ConfigurationCustomizer configurationCustomizer() {
                return configuration -> configuration.setUseDeprecatedExecutor(false);
        }

}
