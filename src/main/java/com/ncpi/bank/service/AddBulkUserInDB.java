package com.ncpi.bank.service;

import com.ncpi.bank.models.User;
import com.ncpi.bank.models.UserFromApi;
import com.ncpi.bank.mongo.MongoConnector;
import com.ncpi.bank.utils.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddBulkUserInDB {

    private final HttpClient httpClient;
    private final MongoConnector mongoConnector;
    private final PasswordEncoder passwordEncoder;

    @Value("${ncpi.user.details.uri}")
    private String url;

    public List<UserFromApi> addUserIntoDb() throws Exception {
        List<UserFromApi> userFromApiList = httpClient.sendGet(url);
        String password = passwordEncoder.encode("123def");
        for (UserFromApi userFromApi : userFromApiList) {
            User user = new User(userFromApi.getId(), userFromApi.getName(), userFromApi.getImage(), 10000, password);
            mongoConnector.save(user);
        }
        return userFromApiList;
    }


}
