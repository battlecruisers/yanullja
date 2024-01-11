package com.battlecruisers.yanullja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class YanulljaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YanulljaApplication.class, args);
	}

}
