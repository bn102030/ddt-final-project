package com.ddt.finalproject.repository;

import com.ddt.finalproject.entity.OrderSummaryEntity;
import com.ddt.finalproject.entity.ShopEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepo extends JpaRepository<ShopEntity, Long> {
    Boolean existsByTax(String tax);

    ShopEntity findByTax(String Tax);
    
//    @Query("select s from ShopEntity s"
//			+ "LEFT JOIN OrderSummaryEntity e on s.shopId = e.shopId "
//			+ "where e.orderId = :#{#orderId}")
//	List<ShopEntity> test(@Param ("orderId") Long orderId);
}
