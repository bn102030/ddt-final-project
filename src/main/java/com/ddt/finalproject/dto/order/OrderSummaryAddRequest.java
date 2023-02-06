package com.ddt.finalproject.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;



@Getter
@Setter
public class OrderSummaryAddRequest {

    private Long userId;
    private Long shopId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;
    private String receipt;





}
