package com.message.api;

import com.message.api.configuration.RsakeysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
@EnableConfigurationProperties(RsakeysConfig.class)
public class ApiApplication {

	public static void main(String[] args) {


		SpringApplication.run(ApiApplication.class, args);
//		UUID uuid = UUID.randomUUID();
//		for (int i = 0; i < 20; i++) {
//			String uuidAsString = uuid.toString();
//			System.out.println("UUID : " + uuidAsString);
//		}

	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
