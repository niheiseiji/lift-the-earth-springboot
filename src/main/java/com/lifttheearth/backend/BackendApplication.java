package com.lifttheearth.backend;

import com.lifttheearth.backend.config.DotenvPropertySourceInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BackendApplication.class);
		app.addInitializers(new DotenvPropertySourceInitializer());
		app.run(args);
	}}
