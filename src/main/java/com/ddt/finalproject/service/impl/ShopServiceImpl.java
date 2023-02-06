package com.ddt.finalproject.service.impl;

import com.ddt.finalproject.dto.ShopDto;
import com.ddt.finalproject.dto.shop.ShopAddRequest;
import com.ddt.finalproject.entity.ShopEntity;
import com.ddt.finalproject.repository.ShopRepo;
import com.ddt.finalproject.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShopServiceImpl implements ShopService {

    private final static Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    ShopRepo shopRepo;

    //查詢單筆店家資料
    public ShopDto getShopData(Long shopId){
        Optional<ShopEntity> empOpt = shopRepo.findById(shopId);
        ShopDto shopDto = new ShopDto();

        if(empOpt.isPresent()){
            ShopEntity shopEntity = empOpt.get();
            shopDto.setShopId(shopEntity.getShopId());
            shopDto.setShopName(shopEntity.getShopName());
            shopDto.setTax(shopEntity.getTax());
        }

        return shopDto;
    }

    //新增店家資料
    @Override
    public Long addShopData(ShopAddRequest shopAddRequest) {
        Boolean ShopTax = shopRepo.existsByTax(shopAddRequest.getSellerTax());
        if(ShopTax){
            log.warn("該商店 {} 已經存在", shopAddRequest.getSellerTax());
            ShopEntity shopEntity = shopRepo.findByTax(shopAddRequest.getSellerTax());
            return shopEntity.getShopId();
        }else {
            log.info("該商店 {} 尚未被登記，可以新增", shopAddRequest.getSellerTax());
            ShopEntity shopEntity = new ShopEntity();
            shopEntity.setShopName(shopAddRequest.getSellerName());
            shopEntity.setTax(shopAddRequest.getSellerTax());
            shopEntity.setAddress(shopAddRequest.getSellerAddress());
            shopRepo.save(shopEntity);
            log.info("該商店 {} 成功新增", shopAddRequest.getSellerTax());
            return shopEntity.getShopId();
        }

    }

}
