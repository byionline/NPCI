package com.ncpi.bank.controllers;

import com.ncpi.bank.models.UserFromApi;
import com.ncpi.bank.service.AddBulkUserInDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/preload")
@RequiredArgsConstructor
@Slf4j
public class BulkUserController {

    private final AddBulkUserInDB addBulkUserInDB;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<UserFromApi> response;
        try {
            response = addBulkUserInDB.addUserIntoDb();
        } catch (Exception e) {
            log.error("Error from Api response", e);
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.ok(response);
    }

}
