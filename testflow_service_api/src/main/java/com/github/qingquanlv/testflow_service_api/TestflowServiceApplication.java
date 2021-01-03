package com.github.qingquanlv.testflow_service_api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;



@MapperScan("com.github.qingquanlv.testflow_service_api.mapper") //扫描的mapper
@SpringBootApplication
public class TestflowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestflowServiceApplication.class, args);
	}

}
