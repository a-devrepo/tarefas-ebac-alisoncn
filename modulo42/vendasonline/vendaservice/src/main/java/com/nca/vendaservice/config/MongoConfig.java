package com.nca.vendaservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.nca.vendaservice.domain.repositories")
public class MongoConfig {
}
