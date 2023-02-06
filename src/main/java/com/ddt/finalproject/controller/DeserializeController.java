package com.ddt.finalproject.controller;

import com.ddt.finalproject.dto.all.AllDataRequest;
import com.ddt.finalproject.dto.order.ItemsAddDto;
import com.ddt.finalproject.dto.order.OrderDetailsAddRequest;
import com.ddt.finalproject.dto.order.OrderSummaryAddRequest;
import com.ddt.finalproject.dto.shop.ShopAddRequest;
import com.ddt.finalproject.service.OrderService;
import com.ddt.finalproject.service.ShopService;
import com.ddt.finalproject.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class DeserializeController {

    @Autowired
    ShopService shopService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @ApiOperation("使用全部資料進行新增")
    @PostMapping("/all")
    public void AddAllData(@RequestBody AllDataRequest allDataRequest) throws IOException {

        Long user_id = userService.getUserIdByCarrierId(allDataRequest.getCarrierID());

        ShopAddRequest shopAddRequest = new ShopAddRequest();
        shopAddRequest.setSellerAddress(allDataRequest.getSellerAddress());
        shopAddRequest.setSellerName(allDataRequest.getSellerName());
        shopAddRequest.setSellerTax(allDataRequest.getSellerTax());
        Long shop_id = shopService.addShopData(shopAddRequest);


        OrderSummaryAddRequest orderSummaryAddRequest = new OrderSummaryAddRequest();
        orderSummaryAddRequest.setShopId(shop_id);
        orderSummaryAddRequest.setUserId(user_id);
        orderSummaryAddRequest.setOrderDate(allDataRequest.getOrderDate());
        orderSummaryAddRequest.setReceipt(allDataRequest.getReceipt());
        Long order_id = orderService.addOrderSummary(orderSummaryAddRequest);

        ArrayList<ItemsAddDto> ItemsList = allDataRequest.getDetails();
        for(ItemsAddDto item:ItemsList){
            OrderDetailsAddRequest orderDetailsAddRequest = new OrderDetailsAddRequest();
            orderDetailsAddRequest.setOrderId(order_id);
            orderDetailsAddRequest.setPrice(item.getPrice());
            orderDetailsAddRequest.setItemName(item.getItemName());
            orderDetailsAddRequest.setAmount(item.getAmount());
            orderDetailsAddRequest.setCategoryId(orderService.getDetailsByCategoryID(item.getItemName()));
            orderService.addOrderDetails(orderDetailsAddRequest);
        }
    }


}
