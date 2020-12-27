package com.ncpi.bank.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Request {

    @NotEmpty
    private String userIdFrom;
    @NotEmpty
    private String userIdTo;

    private Long transferAmount;

    private Long upiCode;
}
