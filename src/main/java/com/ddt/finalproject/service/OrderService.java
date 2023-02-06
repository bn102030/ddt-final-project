package com.ddt.finalproject.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import com.ddt.finalproject.dto.*;
import com.ddt.finalproject.dto.order.OrderDetailsAddRequest;
import com.ddt.finalproject.dto.order.OrderSummaryAddRequest;

import java.util.List;

public interface OrderService {
	

	List<DetailItemsRequest> getOrderDetailByReceipt(String receipt);

	List<SummaryIDRequest> getSummaryIDRequest(Long userID);

	List<OrderDetailsDto> getDetailsByUserID(Long userID) throws IOException;

	List<CategoryPriceDto> getCatrgoryByUserIDAndDate(Long userID,String month);
	Integer getDetailsByCategoryID(String itemName) throws IOException;

	List<OrderDetailsDto> getOrderDetailsByUserID(Long userID);
//
//	List<CategoryPriceDto> getCatrgoryByUserID(Long userID);

	String updateCatrgoryByUserDetailsID(Long orderDetailId,int categoryID);

	Long addOrderSummary(OrderSummaryAddRequest orderSummaryAddRequest);

	void addOrderDetails(OrderDetailsAddRequest orderDetailsAddRequest);

	String GetCurrentYearAndMonth();

//	List<Long> getDetailsByCategoryID(Long order_details_ID) throws IOException;


//	List<String> getDetailsByCategoryID(String itemName);
}
