package com.ddt.finalproject.frontEndService.impl;

import com.ddt.finalproject.dto.CategoryPriceDto;
import com.ddt.finalproject.dto.frontEnd.CategoryAndPrice;
import com.ddt.finalproject.dto.frontEnd.DateFormat;
import com.ddt.finalproject.frontEndService.SpendService;
import com.ddt.finalproject.service.OrderService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpendServiceImpl implements SpendService {

    @Autowired
    OrderService orderService;

    // 當月消費最多的類別
    @Override
    public CategoryAndPrice GetCurrentMaxCategory(Long userID,String dateSelect) {
        List<CategoryPriceDto> categoryPriceList = orderService.getCatrgoryByUserIDAndDate(userID,dateSelect);
        List<CategoryAndPrice> capList = new ArrayList<>();
        for(CategoryPriceDto category:categoryPriceList){
            CategoryAndPrice cap = new CategoryAndPrice();
            cap.setCategoryName(category.getCategoryName());
            cap.setCategoryPrice(category.getPrice());
            capList.add(cap);
        }
        CategoryAndPrice tmp = capList.get(0);
        for(int i =0; i<capList.size();i++){
            if(capList.get(i).getCategoryPrice()>tmp.getCategoryPrice()){
                tmp = capList.get(i);
            }
        }
        if (tmp.getCategoryPrice()==0){
            tmp.setCategoryName("尚未有紀錄");
        }
        return tmp;
    }

    // 當月總金額
    @Override
    public Integer GetCurrentSumPrice(Long userID, String dateSelect) {
        List<CategoryPriceDto> categoryPriceList = orderService.getCatrgoryByUserIDAndDate(userID,dateSelect);
        Integer totalPrice = 0;
        for(CategoryPriceDto category:categoryPriceList){
            totalPrice += category.getPrice();
        }
        return totalPrice;
    }




    // 當月消費最多的類別 vs 上個月該類別 ％數
    @Override
    public Integer GetComparePriceByCategory(Long userID, String dateSelect) {

        CategoryAndPrice currentMax = GetCurrentMaxCategory(userID,"current");
        List<CategoryPriceDto> lastList = orderService.getCatrgoryByUserIDAndDate(userID,dateSelect);
        Integer currentTotal = 0, lastTotal = 0;
        currentTotal = currentMax.getCategoryPrice();
        while (currentMax.getCategoryName().equals("尚未有紀錄")){
            return 0;
        }
        for(CategoryPriceDto lastcategory:lastList){
            if(currentMax.getCategoryName().equals(lastcategory.getCategoryName())){
                lastTotal = lastcategory.getPrice();
                return currentTotal -lastTotal;
            }
        }
        Integer comparePrice = lastTotal - currentTotal;

        return comparePrice;
    }



}
