package com.ncpi.bank.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {

    private String userId;
    private String name;
    private String image;
    private long balance;
    private String password;
    private List<Transation> transationList = new ArrayList<>();

    public User(String userId, String name, String image, String password) {
        this.name = name;
        this.userId = userId;
        this.image = image;
        this.password = password;
    }

    public User(String userId, String name, String password, long balance) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.balance = balance;
    }

    public User(String userId, String name, String image, long balance, String password) {
        this.name = name;
        this.userId = userId;
        this.image = image;
        this.balance = balance;
        this.password = password;
    }
}
