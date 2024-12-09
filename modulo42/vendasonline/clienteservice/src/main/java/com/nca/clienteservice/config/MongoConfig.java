package com.nca.clienteservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.nca.clienteservice.domain.repositories")
public class MongoConfig {
}
