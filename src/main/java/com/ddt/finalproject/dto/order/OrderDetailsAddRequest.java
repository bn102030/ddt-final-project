package com.ddt.finalproject.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsAddRequest {

    private String itemName;
    private Integer amount;
    private Integer price;
    private Long orderId;
    private Integer categoryId;
}
