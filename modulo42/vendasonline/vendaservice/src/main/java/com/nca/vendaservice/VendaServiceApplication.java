package com.nca.vendaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
public class VendaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendaServiceApplication.class, args);
	}

}
