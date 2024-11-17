package com.perfectcollection.billingsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    // This class can be empty; it just needs the annotation to enable auditing
}