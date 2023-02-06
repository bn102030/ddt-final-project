package com.ddt.finalproject.repository;

import com.ddt.finalproject.dto.OrderDetailsDto;
import com.ddt.finalproject.entity.OrderDetailsEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetailsEntity, Long> {

	@Query("select e from OrderDetailsEntity e "
			+ "where e.orderId = :#{#oderIDforQuery}")
	List<OrderDetailsEntity> findDetailByOrderID(@Param ("oderIDforQuery") Long oderIDforQuery);
	
	@Query("select e from OrderDetailsEntity e "
			+ "LEFT JOIN OrderSummaryEntity s on e.orderId = s.orderId "
			+ "where s.receipt = :#{#receiptforQuery}")
	List<OrderDetailsEntity> findDetailByOrderReceipt(@Param ("receiptforQuery") String receipt);

//	List<OrderDetailsEntity> findByName(String itemName);
	Boolean existsByItemNameAndOrderId(String itemName, Long orderId);



}
