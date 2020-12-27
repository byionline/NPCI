package com.ncpi.bank.mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class MongoConnector implements DbConnector {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static Query query;

    @Override
    public <T> T findOneByUserId(String userId, Class<T> tClass) {
        query=Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, tClass);
    }

    @Override
    public <T> T findOneByUserIdAndUpdate(String userId,Update update, Class<T> tClass) {
        query=Query.query(Criteria.where("userId").is(userId));
        return findAndModify(query,update,tClass);
    }

    @Override
    public <T> T findOne(String id,String name, Class<T> tClass) {
        query=Query.query(Criteria.where("userId").is(id));
        return mongoTemplate.findOne(query, tClass);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, FindAndModifyOptions findAndModifyOptions, Class<T> tClass) {
        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, tClass);
    }

    @Override
    public <T> T save(T object) {
        return mongoTemplate.save(object);
    }

    @Override
    public <T> T insert(T t, String collectionName) {
        return mongoTemplate.insert(t, collectionName);
    }

    @Override
    public <T> void upsert(Query query, Update update, Class<T> tClass) {
        mongoTemplate.upsert(query, update, tClass);
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return mongoTemplate.getCollection(collectionName);
    }

    @Override
    public <T> T findAndModify(Query query, Update update, Class<T> tClass) {
        return mongoTemplate.findAndModify(query, update, tClass);
    }
}
