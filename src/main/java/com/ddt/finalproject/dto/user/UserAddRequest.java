package com.ddt.finalproject.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddRequest {


    private String userName;
    private String account;
    private String carrierID;
}
