package com.ncpi.bank.controllers;

import com.ncpi.bank.models.Request;
import com.ncpi.bank.service.TransferFund;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/bank")
public class TransationController {

    private final TransferFund transferFund;

    @Value("${ncpi.upi.code}")
    private long upiCode;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferFund(@Valid @RequestBody Request request) {
        try {
           if(request.getUpiCode()==null || request.getTransferAmount()==null)
                throw new Exception("Please enter a valid upi code and transaction amount");
            if (request.getUpiCode() != upiCode)
                throw new Exception("Please enter a valid upi code");
            return ResponseEntity.ok(transferFund.transferFund(request));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/history")
    private ResponseEntity<?> getHistory(@Valid @RequestParam String userId) {
        try {
            return ResponseEntity.ok(transferFund.getHistory(userId));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/balance")
    private ResponseEntity<?> getBalance(@Valid @RequestParam String userId) {
        try {
            return ResponseEntity.ok(transferFund.getBalance(userId));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/users")
    private ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity.ok(transferFund.getUsers());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
