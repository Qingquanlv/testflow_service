package com.github.qingquanlv.testflow_service_api;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author qingquanlv
 */
@EnableAsync
@MapperScan({"com.github.qingquanlv.testflow_service_api.mapper","com.github.qingquanlv.testflow_service_api.dao"})
@SpringBootApplication
public class TestflowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestflowServiceApplication.class, args);
	}
}

