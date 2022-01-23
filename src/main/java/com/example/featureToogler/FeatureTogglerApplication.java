package com.example.featureToogler;

import com.example.featureToogler.dto.User;
import com.example.featureToogler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FeatureTogglerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeatureTogglerApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Bean
	public void	start(){
		System.out.println("\u001B[36m" +" XXXXX FeatureTooglerApplication XXXXX");

		User karolis = new User();
		karolis.setId(1L);
		karolis.setUserName("Karolis");
		karolis.setRole("ADMIN_USER");
		userRepository.save(karolis);

		User simpleUser = new User();
		karolis.setId(1L);
		karolis.setUserName("Simple_user");
		karolis.setRole("SIMPLE_USER");
		userRepository.save(karolis);
	}

}
