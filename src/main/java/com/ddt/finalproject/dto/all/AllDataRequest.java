package com.ddt.finalproject.dto.all;

import com.ddt.finalproject.dto.order.ItemsAddDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class AllDataRequest {

    private String carrierID;

    private String sellerTax;
    private String sellerName;
    private String sellerAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;
    private String receipt;

    @JsonProperty("details")
    private ArrayList<ItemsAddDto> details;

}
