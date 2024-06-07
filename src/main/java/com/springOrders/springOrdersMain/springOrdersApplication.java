package com.springOrders.springOrdersMain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan(basePackages = "com.springOrders")
@EntityScan(basePackages = "com.springOrders.entities")
@EnableKafka
@EnableJpaRepositories
public class springOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(springOrdersApplication.class, args);
	}

}
