package com.ddt.finalproject.controller;

import com.ddt.finalproject.dto.ShopDto;
import com.ddt.finalproject.dto.shop.ShopAddRequest;
import com.ddt.finalproject.dto.user.UserAddRequest;
import com.ddt.finalproject.service.ShopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopController {

    @Autowired
    ShopService shopService;



    @ApiOperation("取得單筆店家資料(shopID)")
    @GetMapping("/shop/{shopId}")
    public ShopDto getShopDataByName(@PathVariable Long shopId){
       ShopDto shopDto = shopService.getShopData(shopId);
       return shopDto;
    }

    @ApiOperation("新增單筆店家資料")
    @PostMapping("/shop")
    public void addShopData(@RequestBody ShopAddRequest shopAddRequest) {
        shopService.addShopData(shopAddRequest);
    }
}
