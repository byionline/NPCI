package com.ncpi.bank.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transation {

    private String process;
    private long amount;
}
