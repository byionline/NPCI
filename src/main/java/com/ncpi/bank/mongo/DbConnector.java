package com.ncpi.bank.mongo;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public interface DbConnector {

    <T> T findOneByUserId(String userId, Class<T> tClass);

    <T> T findOne(String email,String username, Class<T> tClass);

    <T> T findOneByUserIdAndUpdate(String userId,Update update, Class<T> tClass);

    <T> T findAndModify(Query query, Update update, FindAndModifyOptions findAndModifyOptions, Class<T> tClass);

    <T> T save(T object);

    <T> T insert(T t, String collectionName);

    <T> void upsert(Query query, Update update, Class<T> tClass);

    <T> T findAndModify(Query query, Update update, Class<T> tClass);

}
