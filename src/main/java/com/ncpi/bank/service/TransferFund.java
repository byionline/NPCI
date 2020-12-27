package com.ncpi.bank.service;

import com.ncpi.bank.models.Request;
import com.ncpi.bank.models.Transation;
import com.ncpi.bank.models.User;
import com.ncpi.bank.models.UserFromApi;
import com.ncpi.bank.mongo.MongoConnector;
import com.ncpi.bank.utils.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferFund {

    private final MongoConnector mongoConnector;
    private final HttpClient httpClient;

    @Value("${ncpi.user.details.uri}")
    private String url;

    public String transferFund(Request request) throws Exception {
        Object lock1 = request.getUserIdFrom().compareTo(request.getUserIdTo()) > 0 ? request.getUserIdFrom() : request.getUserIdTo();
        Object lock2 = request.getUserIdFrom().compareTo(request.getUserIdTo()) > 0 ? request.getUserIdTo() : request.getUserIdFrom();
        synchronized (lock1) {
            synchronized (lock2) {
                boolean success=withdrawal(request.getUserIdFrom(), request.getTransferAmount());
                if(success)
                    send(request.getUserIdTo(), request.getTransferAmount());
                else
                    throw new Exception("The transferred amount is more than the actual balance");
            }
        }
        return "Successful";
    }

    private void send(String userIdTo, long transferAmount) throws Exception {
        User user=mongoConnector.findOneByUserId(userIdTo,User.class);
        if(user==null)
            throw new Exception("Sending user Doesn't exist");
        List<Transation> transations=user.getTransationList();
        long balance=user.getBalance()+transferAmount;
        transations.add(new Transation("Received Money",transferAmount));
        Update update=new Update();
        update.set("balance",balance);
        update.set("transationList",transations);
        mongoConnector.findOneByUserIdAndUpdate(userIdTo,update,User.class);
    }

    private boolean withdrawal(String userIdFrom, long transferAmount) throws Exception {
        User user=mongoConnector.findOneByUserId(userIdFrom,User.class);
        if(user==null)
            throw new Exception("Recieving user Doesn't exist");
        if(user.getBalance()>=transferAmount){
            List<Transation> transations=user.getTransationList();
            long balance=user.getBalance()-transferAmount;
            transations.add(new Transation("Sent Money",transferAmount));
            Update update=new Update();
            update.set("balance",balance);
            update.set("transationList",transations);
            mongoConnector.findOneByUserIdAndUpdate(userIdFrom,update,User.class);
        }else
            return false;
        return true;
    }

    public List<Transation> getHistory(String userId) throws Exception {
        User user=mongoConnector.findOneByUserId(userId,User.class);
        if(user==null)
            throw new Exception("User Doesn't exist");
        return user.getTransationList();
    }

    public long getBalance(String userId) throws Exception {
        User user=mongoConnector.findOneByUserId(userId,User.class);
        if(user==null)
            throw new Exception("User Doesn't exist");
        return user.getBalance();
    }

    public List<UserFromApi> getUsers() throws Exception {
        return httpClient.sendGet(url);
    }
}
