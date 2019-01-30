package com.twods;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TwodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwodsApplication.class, args);
	}

	@Bean
    public CommandLineRunner dumpRegisteredBeans(ApplicationContext appContext) {
        return args -> {
            String[] beans = appContext.getBeanDefinitionNames();
            Arrays.stream(beans).sorted().forEach( bean ->{
            	System.out.println("Bean: > "+bean);
            });
        };
    }
}

