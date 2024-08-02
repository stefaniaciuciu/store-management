package com.store.project;

import com.store.project.controller.UserController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProjectApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(ProjectApplicationTests.class);
	@Test
	void contextLoads() {
		assertTrue(true);
		logger.info("test");
	}

}
