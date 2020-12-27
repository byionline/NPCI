package com.ncpi.bank.mongo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@Slf4j
public class MongoConfiguration{

    private final MongoProperties mongoProperties;

    public MongoConfiguration(MongoProperties mongoProperties){
        this.mongoProperties = mongoProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = "mongoClient", value = MongoClient.class)
    public MongoClient mongoClient() {
        log.info("Creating MongoClient");
        if (mongoProperties.getPassword() == null || mongoProperties.getUser() == null
                || StringUtils.isBlank(mongoProperties.getPassword()) || StringUtils.isBlank(mongoProperties.getUser()))
            return MongoClients.create(mongoProperties.toMongoClientSettings());
        return MongoClients.create(mongoProperties.toMongoClientSettingsWithAuthentication());
    }

    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @Bean
    @ConditionalOnMissingBean(name = "mongoTemplate", value = MongoTemplate.class)
    public MongoTemplate mongoTemplate(){
        log.info("creating mongoTemplate");
        return new MongoTemplate(mongoClient(),getDatabaseName());
    }
}
