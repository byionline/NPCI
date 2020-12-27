package com.ncpi.bank.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ncpi.bank.models.UserFromApi;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class HttpClient {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final ObjectMapper objectMapper=new ObjectMapper();
    private static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    public List<UserFromApi> sendGet(String uri) throws Exception {
        HttpGet request = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(request);
        try{
            HttpEntity entity = response.getEntity();
            return parseList(EntityUtils.toString(entity),UserFromApi.class);
        }finally {
            response.close();
        }
    }

    public static <T> JavaType listType(Class<T> clazz) {
        return typeFactory.constructCollectionType(List.class, clazz);
    }

    public static <T> List<T> parseList(String str, Class<T> clazz)
            throws IOException {
        return objectMapper.readValue(str, listType(clazz));
    }
}
