package com.ddt.finalproject.repository;

import com.ddt.finalproject.dto.OrderSummaryDto;
import com.ddt.finalproject.dto.SummaryDateDto;
import com.ddt.finalproject.dto.SummaryIDRequest;
import com.ddt.finalproject.entity.OrderDetailsEntity;
import com.ddt.finalproject.entity.OrderSummaryEntity;
import com.ddt.finalproject.entity.ShopEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderSummaryRepo extends JpaRepository<OrderSummaryEntity, Long> {

    @Query("SELECT o FROM OrderSummaryEntity o WHERE o.userId=:userID")
    List<OrderSummaryEntity> findOrderSummaryByUserID(Long userID);
    
    Boolean existsByReceipt(String receipt);

    OrderSummaryEntity findByReceipt(String receipt);

    //List<SummaryDateDto> findByDropDateBetween(Date startDate, Date endDate);
    
    @Query("select e.orderDate from OrderSummaryEntity e "
			+ "LEFT JOIN OrderDetailsEntity s on e.orderId = s.orderId "
			+ "where s.orderDetailId = :#{#orderDetailId}")
	Date getOrderDateByDetailID(@Param ("orderDetailId") Long orderDetailId);
    
    @Query("select e.shopId from OrderSummaryEntity e "
			+ "LEFT JOIN OrderDetailsEntity s on e.orderId = s.orderId "
			+ "where s.orderDetailId = :#{#orderDetailId}")
	Long getShopIDByDetailID(@Param ("orderDetailId") Long orderDetailId);
    
    @Query("select s.shopName from ShopEntity s "
    		+ "where s.shopId = :#{#shopId}")
    String getShopNameByDetailID(@Param ("shopId") Long shopId);
    
//    @Query("select s.shopName from ShopEntity s "
//			+ "LEFT JOIN OrderSummaryEntity e on s.shopId = e.shopId "
//			+ "where e.orderId = :#{#orderId}")
//	List<String> getShopNameByUserID(@Param ("orderId") Long orderId);
    
    
}
