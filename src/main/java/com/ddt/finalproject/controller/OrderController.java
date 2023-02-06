package com.ddt.finalproject.controller;

import com.ddt.finalproject.dto.CategoryPriceDto;
import com.ddt.finalproject.dto.DetailItemsRequest;

import com.ddt.finalproject.dto.OrderDetailsDto;
import com.ddt.finalproject.dto.SummaryDateDto;
import com.ddt.finalproject.dto.SummaryIDRequest;
import com.ddt.finalproject.dto.order.OrderSummaryAddRequest;
import com.ddt.finalproject.repository.OrderSummaryRepo;
import com.ddt.finalproject.response.ReceiptResponseHandler;
import com.ddt.finalproject.service.OrderService;
import io.swagger.annotations.ApiOperation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.bytebuddy.asm.Advice;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    // 待整合_Eric
    @Autowired
    private OrderSummaryRepo orderSummaryRepo;


    @ApiOperation("新增發票資訊(userID+shopID)")
    @PostMapping("/orderSummary")
    public void addOrderSummay(@RequestBody OrderSummaryAddRequest orderSummaryAddRequest) {
        orderService.addOrderSummary(orderSummaryAddRequest);

    }

   
    @ApiOperation("查詢使用者的發票流水號清單(userID)")
    @GetMapping("/orderSummary/{userID}")
    public List<SummaryIDRequest> getSummaryIDRequest(@PathVariable Long userID) {
        List<SummaryIDRequest> summaryIDRequest = orderService.getSummaryIDRequest(userID);
        return summaryIDRequest;
    }


    // 主功能
    @ApiOperation("查詢使用者訂單明細(整合)")
    @GetMapping("/orderSummaries/{userID}")
    public List<OrderDetailsDto> getOrderDetailsByUserID(@PathVariable Long userID) {
        List<OrderDetailsDto> summaryData = orderService.getOrderDetailsByUserID(userID);
        return summaryData;
    }

    
    // 主功能_使用年月份和userID查詢使用者分類金額(整合)
    @ApiOperation("查詢使用者分類金額(Date(all|current|yyyy-MM)+userID)")
    @PostMapping("/orderSummary/CategoryDetail")
    public List<CategoryPriceDto> getCatrgoryByUserIDAndDate(@RequestParam Long userID, @RequestParam String month) {
        List<CategoryPriceDto> categoryData = orderService.getCatrgoryByUserIDAndDate(userID, month);
        return categoryData;
    }

    // 主功能
//    @ApiOperation("查詢使用者分類金額(userID)")
//    @GetMapping("/orderSummary/CategoryDetail/{userID}")
//    public List<CategoryPriceDto> getCatrgoryByUserID(@PathVariable Long userID) {
//        List<CategoryPriceDto> categoryData = orderService.getCatrgoryByUserID(userID);
//        return categoryData;
//    }

    // 主功能_透過 orderDetailID 修改類別
    @ApiOperation("修改品項分類(orderDetailID+categoryID)")
    @PutMapping("/orderSummary/updateCategoryID")
    public String updateCatrgoryByUserDetailsID(@RequestParam Long orderDetailId, @RequestParam int categoryID) {
        String result = orderService.updateCatrgoryByUserDetailsID(orderDetailId, categoryID);
        return result;

    }

    //子功能
    @ApiOperation("查詢訂單明細 (receipt)")
    @GetMapping("/orderDetail/{receipt}")
    public ResponseEntity<Object> getOrderDetailByReceipt(@PathVariable String receipt) {
//	  List<DetailItemsRequest> orderDetailList =  orderService.getOrderDetailByReceipt(receipt);
//      return orderDetailList;
        return ReceiptResponseHandler.responseBuiler(receipt, HttpStatus.OK,
                orderService.getOrderDetailByReceipt(receipt));
    }


    @ApiOperation("自動分類(itemName)")
    @GetMapping("/orderDetails/{itemName}")
    public Integer getDetailsByCategoryID(@PathVariable String itemName) throws IOException {
        Integer categoryID = orderService.getDetailsByCategoryID(itemName);
//		List<OrderDetailsDto> detailsDate = orderService.getDetailsByCategoryID(itemName);
        return categoryID;
    }

    /*
     * @ApiOperation("Get date range")
     *
     * @RequestMapping("/ordersummary") public List<SummaryDateDto>
     * findBetween(@RequestBody Map<String, Object> params){ Date startDate = null;
     * Date endDate = null;
     *
     * try{ startDate = DateUtils.parseDate((String)params.get("startDate"),
     * "2022"); endDate = DateUtils.parseDate((String)params.get("endDate"),
     * "2022"); }catch (ParseException e){ e.printStackTrace(); }
     *
     *
     * return OrderSummaryRepo.findByDropDateBetween(startDate, endDate); }
     */


//	  @ApiOperation("Get date range")
//
//	 @PostMapping("/ordersummary") public List<SummaryDateDto>
//	 findBetween(@RequestBody Map<String, Object> params){
//		  Date startDate = null;
//		  Date endDate = null;
//	 try{ startDate = DateUtils.parseDate((String)params.get("startDate"),
//	 "2022"); endDate = DateUtils.parseDate((String)params.get("endDate"),
//	 "2022"); }catch (ParseException e){ e.printStackTrace(); }
//
//
//	 return OrderSummaryRepo.findByDropDateBetween(startDate, endDate); }

}