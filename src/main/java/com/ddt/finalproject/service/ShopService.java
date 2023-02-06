package com.ddt.finalproject.service;

import com.ddt.finalproject.dto.ShopDto;
import com.ddt.finalproject.dto.shop.ShopAddRequest;


public interface ShopService {
    ShopDto getShopData(Long shopId);

    Long addShopData(ShopAddRequest shopAddRequest);
}
