package com.nca.produtoservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.nca.produtoservice.domain.repositories")
public class MongoConfig {
}
