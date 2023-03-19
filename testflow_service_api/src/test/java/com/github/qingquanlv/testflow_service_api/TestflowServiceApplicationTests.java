package com.github.qingquanlv.testflow_service_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestflowServiceApplicationTests {

	@Test
	void contextLoads() {
		Object a = 1111;
		Double b = Double.valueOf(a.toString());
		System.out.println(b);
	}

}
