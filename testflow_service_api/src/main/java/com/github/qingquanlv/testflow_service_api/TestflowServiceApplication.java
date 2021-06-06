package com.github.qingquanlv.testflow_service_api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author qingquanlv
 */
@EnableAsync
//@MapperScan("com.github.qingquanlv.testflow_service_api.DAO") //扫描的mapper
@MapperScan("com.github.qingquanlv.testflow_service_api.mapper")
@SpringBootApplication
public class TestflowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestflowServiceApplication.class, args);
	}

//	@Bean("threadPoolTaskExecutor")
//	public TaskExecutor getAsyncExedcutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(20);
//		executor.setMaxPoolSize(1000);
//		executor.setWaitForTasksToCompleteOnShutdown(true);
//		executor.setThreadNamePrefix("Async-");
//		return executor;
//	}

}


