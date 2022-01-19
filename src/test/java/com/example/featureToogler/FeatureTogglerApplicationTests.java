package com.example.featureToogler;

import com.example.featureToogler.service.FeatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@ContextConfiguration
class FeatureTogglerApplicationTests {

	@Autowired
	FeatureService featureService;

	@BeforeEach
	void setUp() {
		System.out.println("XXXX Test started XXXX");
	}

	@Test
	void contextLoads() {
	}

}
