package com.ddt.finalproject.service.impl;

import com.ddt.finalproject.dto.SummaryIDRequest;

import com.ddt.finalproject.dto.*;

import com.ddt.finalproject.dto.order.OrderDetailsAddRequest;
import com.ddt.finalproject.dto.order.OrderSummaryAddRequest;
import com.ddt.finalproject.entity.CategoryEntity;
import com.ddt.finalproject.entity.OrderDetailsEntity;
import com.ddt.finalproject.entity.OrderSummaryEntity;

import com.ddt.finalproject.entity.ShopEntity;
import com.ddt.finalproject.repository.*;

import com.ddt.finalproject.service.OrderService;


import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ddt.finalproject.service.ShopService;

import org.apache.commons.lang3.time.DateUtils;
import com.huaban.analysis.jieba.JiebaSegmenter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ddt.finalproject.utils.DictionaryCsvUtil.getBookkeepingMap;


@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static JiebaSegmenter segmenter = new JiebaSegmenter();

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    //行尾分隔符定義
    private final static String NEW_LINE_SEPARATOR = "\n";
    //上傳文件的存儲位置
    private final static URL PATH = Thread.currentThread().getContextClassLoader().getResource("");

    private Optional<Object> list;
    @Autowired
    OrderDetailsRepo orderDetailsRepo;

	@Autowired
	OrderSummaryRepo orderSummaryRepo;


	@Autowired
	CategoryRepo CategoryRepo;

	@Autowired
	UserRepo userRepo;


	@Autowired
	ShopRepo shopRepo;
	
	@Autowired
	ShopService shopService;
	


    public List<DetailItemsRequest> getOrderDetailByReceipt(String receipt) {
        List<OrderDetailsEntity> orderDetailsEntities = orderDetailsRepo.findDetailByOrderReceipt(receipt);
        List<DetailItemsRequest> result = new ArrayList<>();
        for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntities) {
            DetailItemsRequest detailItemsRequest = new DetailItemsRequest();
            detailItemsRequest.setOrderDetailId(orderDetailsEntity.getOrderDetailId());
            detailItemsRequest.setItemName(orderDetailsEntity.getItemName());
            detailItemsRequest.setPrice(orderDetailsEntity.getPrice());
            detailItemsRequest.setAmount(orderDetailsEntity.getAmount());
            detailItemsRequest.setCategoryId(orderDetailsEntity.getCategoryId());
            detailItemsRequest.setOrderId(orderDetailsEntity.getOrderId());
            result.add(detailItemsRequest);
        }
        return result;
    }



    @Override
    public List<SummaryIDRequest> getSummaryIDRequest(Long userID) {
        List<OrderSummaryEntity> orderSummaryEntities = orderSummaryRepo.findOrderSummaryByUserID(userID);
        List<SummaryIDRequest> orderIDResult = new ArrayList<>();



		for (OrderSummaryEntity orderSummaryEntity : orderSummaryEntities) {
			SummaryIDRequest summaryIDRequest = new SummaryIDRequest();
			summaryIDRequest.setOrderId(orderSummaryEntity.getOrderId());
			orderIDResult.add(summaryIDRequest);
		}
		return orderIDResult;
	}

    //結巴分詞
    public List<String> getSignalWord(String words) {
        List<String> resultList = segmenter.sentenceProcess(words);
        return resultList;
    }




	@Override
    public Integer getDetailsByCategoryID(String itemName) throws IOException {
//        List<OrderDetailsEntity> orderDetailsEntities = orderDetailsRepo.findAll();
//        List<Long> categoryIDResult = new ArrayList<>();

        Integer categoryIDResult = 0;
        Map<String, String> map = getBookkeepingMap();
        Set<String> raws = map.keySet();

//        for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntities) {

            for (String csvRow : raws) {
//
                List<String> cutList = getSignalWord(itemName);
				System.out.println(cutList);
                for(String cutItem: cutList){
//                    System.out.println(cutItem);
                    if(cutItem.equals(csvRow)){
                        categoryIDResult = Integer.valueOf(map.get(cutItem));


//                        System.out.println("cutItem=csvRow"+cutItem+"="+csvRow);
//
//                        System.out.println("value:"+map.get(cutItem));
//                        System.out.println("---");
//				System.out.println(orderDetailsEntity.getItemName()
                    }
                }

//					System.out.println("csvRow:"+csvRow);
////                    System.out.println(cut);
////                cut.replaceAll("\\p{Punct}", "")
//					if (cut.equals(csvRow)) {
//						System.out.println(cut);
//                        System.out.println(csvRow);
//                        System.out.println("===");
////					    categoryIDResult.add(map.get(i));
//                    }

            }
        return categoryIDResult;
//        }



    }


    @Override
    public List<OrderDetailsDto> getOrderDetailsByUserID(Long userID) {
        List<OrderSummaryEntity> orderSummaryEntities = orderSummaryRepo.findOrderSummaryByUserID(userID);
        List<Long> orderIDResult = new ArrayList<>();

        for (OrderSummaryEntity orderSummaryEntity : orderSummaryEntities) {
            SummaryIDRequest summaryIDRequest = new SummaryIDRequest();
            summaryIDRequest.setOrderId(orderSummaryEntity.getOrderId());
            orderIDResult.add(summaryIDRequest.getOrderId());
        }

        List<Long> orderIDList = orderIDResult;
        List<OrderDetailsDto> result = new ArrayList<>();
        for (Long orderIDforQueryInteger : orderIDList) {
            List<OrderDetailsEntity> orderDetailsEntities = orderDetailsRepo.findDetailByOrderID(orderIDforQueryInteger);

            for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntities) {
                OrderDetailsDto detailItemsRequest = new OrderDetailsDto();
                detailItemsRequest.setOrderDetailId(orderDetailsEntity.getOrderDetailId());
                detailItemsRequest.setItemName(orderDetailsEntity.getItemName());
                detailItemsRequest.setPrice(orderDetailsEntity.getPrice());
                detailItemsRequest.setAmount(orderDetailsEntity.getAmount());
                detailItemsRequest.setCategoryId(orderDetailsEntity.getCategoryId());
                result.add(detailItemsRequest);
            }
        }

        return result;
    }

	@Override
	public List<OrderDetailsDto> getDetailsByUserID(Long userID) throws IOException {
		List<OrderSummaryEntity> orderSummaryEntities = orderSummaryRepo.findOrderSummaryByUserID(userID);
		List<Long> orderIDResult = new ArrayList<>();
		for (OrderSummaryEntity orderSummaryEntity : orderSummaryEntities) {
			SummaryIDRequest summaryIDRequest = new SummaryIDRequest();
			summaryIDRequest.setOrderId(orderSummaryEntity.getOrderId());
			orderIDResult.add(summaryIDRequest.getOrderId());
		}

		List<Long> orderIDList = orderIDResult;
		List<OrderDetailsDto> OrderDetailResult = new ArrayList<>();
		for (Long orderIDforQueryInteger : orderIDList) {
			List<OrderDetailsEntity> orderDetailsEntities = orderDetailsRepo
					.findDetailByOrderID(orderIDforQueryInteger);

			for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntities) {
				OrderDetailsDto detailItemsRequest = new OrderDetailsDto();
				detailItemsRequest.setOrderDetailId(orderDetailsEntity.getOrderDetailId());
				detailItemsRequest.setItemName(orderDetailsEntity.getItemName());
				detailItemsRequest.setPrice(orderDetailsEntity.getPrice());
				detailItemsRequest.setAmount(orderDetailsEntity.getAmount());
				detailItemsRequest.setCategoryId(orderDetailsEntity.getCategoryId());
				OrderDetailResult.add(detailItemsRequest);
			}
		}

		return OrderDetailResult;
	}

	// 透過 userID 跟 Date 獲取使用者明細，輸出各類別價格
		@Override
		public List<CategoryPriceDto> getCatrgoryByUserIDAndDate(Long userID, String month) {

		OrderServiceImpl orderServiceImpl = new OrderServiceImpl();

		String currentYearAndMonth = orderServiceImpl.GetCurrentYearAndMonth();

		if (month.equals("current"))// 當月
		{
			currentYearAndMonth = orderServiceImpl.GetCurrentYearAndMonth();
		} else if (month.equals("all"))// 總合
		{
			currentYearAndMonth = "0";
		} else {
			currentYearAndMonth = month;
		}

		List<OrderSummaryEntity> orderSummaryEntities = orderSummaryRepo.findOrderSummaryByUserID(userID);
		List<Long> orderIDResult = new ArrayList<>();

		for (OrderSummaryEntity orderSummaryEntity : orderSummaryEntities) {

			Date createDate = orderSummaryEntity.getOrderDate();
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM");

			String createYearAndMonth = df.format(createDate);
			if (currentYearAndMonth.equals("0")) {
				SummaryIDRequest summaryIDRequest = new SummaryIDRequest();
				summaryIDRequest.setOrderId(orderSummaryEntity.getOrderId());
				orderIDResult.add(summaryIDRequest.getOrderId());
			}

			else if (createYearAndMonth.equals(currentYearAndMonth)) {
				SummaryIDRequest summaryIDRequest = new SummaryIDRequest();
				summaryIDRequest.setOrderId(orderSummaryEntity.getOrderId());
				orderIDResult.add(summaryIDRequest.getOrderId());
			}
		}
		List<Long> orderIDList = orderIDResult;
		List<CategoryPriceDto> categoryResult = orderIDtoCategoryResult(orderIDList);

		return categoryResult;
	}



// 透過 userID 獲取使用者明細，輸出各類別價格
//
//	@Override
//	public List<CategoryPriceDto> getCatrgoryByUserID(Long userID) {
//
//		OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
//
//		String currentYearAndMonth = orderServiceImpl.GetCurrentYearAndMonth();
//
//		List<OrderSummaryEntity> orderSummaryEntities = orderSummaryRepo.findOrderSummaryByUserID(userID);
//		List<Long> orderIDResult = new ArrayList<>();
//
//		for (OrderSummaryEntity orderSummaryEntity : orderSummaryEntities) {
//
//			Date createDate = orderSummaryEntity.getOrderDate();
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
//			String createYearAndMonth = df.format(createDate);
//			if (createYearAndMonth.equals(currentYearAndMonth)) {
//				SummaryIDRequest summaryIDRequest = new SummaryIDRequest();
//				summaryIDRequest.setOrderId(orderSummaryEntity.getOrderId());
//				orderIDResult.add(summaryIDRequest.getOrderId());
//			}
//		}
//		List<Long> orderIDList = orderIDResult;
//		List<CategoryPriceDto> categoryResult = orderIDtoCategoryResult(orderIDList);
//
//		return categoryResult;
//	}

	public List<CategoryPriceDto> orderIDtoCategoryResult(List<Long> orderIDList) {

		List<OrderDetailsDto> OrderDetailresult = new ArrayList<>();
		for (Long orderIDforQueryInteger : orderIDList) {
			List<OrderDetailsEntity> orderDetailsEntities = orderDetailsRepo
					.findDetailByOrderID(orderIDforQueryInteger);

			for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntities) {
				Date entityDate = orderSummaryRepo
						.getOrderDateByDetailID(orderDetailsEntity.getOrderDetailId());
				Long entityShopID = orderSummaryRepo
						.getShopIDByDetailID(orderDetailsEntity.getOrderDetailId());
				String entityShopName = orderSummaryRepo.getShopNameByDetailID(entityShopID);

				OrderDetailsDto detailItemsRequest = new OrderDetailsDto();
				detailItemsRequest.setOrderDetailId(orderDetailsEntity.getOrderDetailId());
				detailItemsRequest.setItemName(orderDetailsEntity.getItemName());
				detailItemsRequest.setPrice(orderDetailsEntity.getPrice());
				detailItemsRequest.setAmount(orderDetailsEntity.getAmount());
				detailItemsRequest.setCategoryId(orderDetailsEntity.getCategoryId());
				detailItemsRequest.setOrderId(orderDetailsEntity.getOrderId());
				detailItemsRequest.setOrderDate(entityDate);
				detailItemsRequest.setShopName(entityShopName);
				OrderDetailresult.add(detailItemsRequest);
			}

		}

		List<CategoryEntity> categoryEntities = CategoryRepo.findAll();
		List<CategoryPriceDto> result = new ArrayList<>();
		int[] categoryPriceSummary = new int[categoryEntities.size() + 1];

		// 計算類別價格
		double sumPrice = 0;
		ArrayList<ArrayList<CategoryDetailsDto>> perCategoryDetail = new ArrayList<>(categoryEntities.size());
		for (int i = 0; i < categoryEntities.size(); i++) {
			perCategoryDetail.add(new ArrayList());
		}
		// 計算類別價格 (從entities拿)
		for (OrderDetailsDto orderDetailsDto : OrderDetailresult) {

			categoryPriceSummary[orderDetailsDto.getCategoryId()] += orderDetailsDto.getAmount()
							* orderDetailsDto.getPrice();

					CategoryDetailsDto categoryDetailsDto = new CategoryDetailsDto();
					categoryDetailsDto.setOrderDetailId(orderDetailsDto.getOrderDetailId());
					categoryDetailsDto.setItemName(orderDetailsDto.getItemName());
					categoryDetailsDto.setAmount(orderDetailsDto.getAmount());
					categoryDetailsDto.setPrice(orderDetailsDto.getPrice());
					categoryDetailsDto.setSumPrice(orderDetailsDto.getAmount() * orderDetailsDto.getPrice());
					categoryDetailsDto.setOrderDate(orderDetailsDto.getOrderDate());
					categoryDetailsDto.setShopName(orderDetailsDto.getShopName());
					perCategoryDetail.get(orderDetailsDto.getCategoryId()).add(categoryDetailsDto);

					sumPrice += orderDetailsDto.getAmount() * orderDetailsDto.getPrice();

		}
		// 將資料回傳
		for (CategoryEntity categoryEntity : categoryEntities) {
			CategoryPriceDto categoryDto = new CategoryPriceDto();
			categoryDto.setPrice(categoryPriceSummary[categoryEntity.getCategoryId()]);
			categoryDto.setCategoryName(categoryEntity.getCategoryName());
			double percentage = Math.round(((categoryPriceSummary[categoryEntity.getCategoryId()]/ sumPrice) * 100.0) * 100.0)
					/ 100.0;
			categoryDto.setPercentage(percentage + "%");

			categoryDto.setOrderList(perCategoryDetail.get(categoryEntity.getCategoryId()));
			result.add(categoryDto);
		}
		return result;
	}

	@Override
	public String updateCatrgoryByUserDetailsID(Long orderDetailId, int categoryId) {
		String result = "尚未更新成功";
//		OrderDetailsEntity orderDetailsEntities = new OrderDetailsEntity();
		Optional<OrderDetailsEntity> orderDetailsOpt = orderDetailsRepo.findById(orderDetailId);
		if(orderDetailsOpt.isPresent()) {
			OrderDetailsEntity orderDetailsEntity = orderDetailsOpt.get();

			orderDetailsEntity.setCategoryId(categoryId);
			OrderDetailsEntity neworderDetailsEntity = orderDetailsRepo.save(orderDetailsEntity);
			result = "成功將物品編號" + orderDetailId + "的分類編號，更新為" + neworderDetailsEntity.getCategoryId();
		}
		return result;
	}

	@Override
	public Long addOrderSummary(OrderSummaryAddRequest orderSummaryAddRequest) {
		Boolean OrderReceipt = orderSummaryRepo.existsByReceipt(orderSummaryAddRequest.getReceipt());
		if (OrderReceipt) {
			log.warn("該發票 {} 已存在", orderSummaryAddRequest.getReceipt());
			OrderSummaryEntity orderSummaryEntity = orderSummaryRepo.findByReceipt(orderSummaryAddRequest.getReceipt());
			return orderSummaryEntity.getOrderId();
		} else {
			log.info("該發票 {} 不存在，可以新增", orderSummaryAddRequest.getReceipt());
			OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
			orderSummaryEntity.setUserId(orderSummaryAddRequest.getUserId());
			orderSummaryEntity.setShopId(orderSummaryAddRequest.getShopId());
			orderSummaryEntity.setOrderDate(orderSummaryAddRequest.getOrderDate());
			orderSummaryEntity.setReceipt(orderSummaryAddRequest.getReceipt());
			orderSummaryRepo.save(orderSummaryEntity);
			OrderSummaryEntity newOrderSummaryEntity = orderSummaryRepo
					.findByReceipt(orderSummaryAddRequest.getReceipt());
			log.info("該發票 {} 成功新增", orderSummaryAddRequest.getReceipt());
			return newOrderSummaryEntity.getOrderId();
		}

    }

    @Override
    public void addOrderDetails(OrderDetailsAddRequest orderDetailsAddRequest) {
        Boolean OrderDetails = orderDetailsRepo.existsByItemNameAndOrderId(orderDetailsAddRequest.getItemName(), orderDetailsAddRequest.getOrderId());
        System.out.println("getItemName" + orderDetailsAddRequest.getItemName());
        System.out.println("getOrderId" + orderDetailsAddRequest.getOrderId());

        if (OrderDetails) {
            log.warn("該明細 {} 已存在", orderDetailsAddRequest.getItemName());
        } else {
            OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
            orderDetailsEntity.setAmount(orderDetailsAddRequest.getAmount());
            orderDetailsEntity.setPrice(orderDetailsAddRequest.getPrice());
            orderDetailsEntity.setItemName(orderDetailsAddRequest.getItemName());
            orderDetailsEntity.setOrderId(orderDetailsAddRequest.getOrderId());
			orderDetailsEntity.setCategoryId(orderDetailsAddRequest.getCategoryId());
            orderDetailsRepo.save(orderDetailsEntity);
            log.info("該明細 {} 已儲存", orderDetailsEntity.getOrderDetailId());
        }
    }





	@Override
	public String GetCurrentYearAndMonth() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
	}

}
