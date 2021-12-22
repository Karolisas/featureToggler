package com.example.featureToogler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FeatureTogglerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatureTogglerApplication.class, args);
	}

	@Bean
	public void	start(){
		System.out.println("XXXXX************** FeatureTooglerApplication XXXXX");
	}

}
