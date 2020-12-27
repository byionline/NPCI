package com.ncpi.bank.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequest {

    @NotBlank
    @Size(min = 5, max = 50)
    private String name;
 
    @NotBlank
    @Size(min = 5, max = 20)
    private String userId;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
