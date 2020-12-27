package com.ncpi.bank.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFromApi {

    @JsonProperty("Image")
    private String image;
    private String id;
    private String name;

}
