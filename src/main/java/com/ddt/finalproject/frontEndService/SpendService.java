package com.ddt.finalproject.frontEndService;

import com.ddt.finalproject.dto.frontEnd.CategoryAndPrice;
import io.swagger.models.auth.In;

public interface SpendService
{

    // 當月總金額


    // 當月消費最多的類別
    CategoryAndPrice GetCurrentMaxCategory(Long userID,String dateSelect);

    Integer GetCurrentSumPrice(Long userID,String dateSelect);

    Integer GetComparePriceByCategory(Long userID,String dateSelect);


    // 當月消費最多的類別 vs 上個月該類別 ％數
}
