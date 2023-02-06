package com.ddt.finalproject.service.impl;

import com.ddt.finalproject.dto.CategoryDto;
import com.ddt.finalproject.dto.CategoryPriceDto;
import com.ddt.finalproject.dto.DetailItemsRequest;
import com.ddt.finalproject.entity.CategoryEntity;
import com.ddt.finalproject.entity.OrderDetailsEntity;
import com.ddt.finalproject.repository.CategoryRepo;
import com.ddt.finalproject.repository.OrderDetailsRepo;
import com.ddt.finalproject.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo CategoryRepo;

	@Autowired
	OrderDetailsRepo OrderDetailsRepo;

	@Override
	public List<CategoryPriceDto> getCategoryDataList() {
		List<CategoryEntity> categoryEntities = CategoryRepo.findAll();
		List<OrderDetailsEntity> orderdetailsEntities = OrderDetailsRepo.findAll();
		List<CategoryPriceDto> result = new ArrayList<>();

		int[][] summary = new int[categoryEntities.size() + 1][2];

		
		//計算類別價格 (從entities拿)
		for (OrderDetailsEntity orderDetailsEntity : orderdetailsEntities) {
			for (CategoryEntity categoryEntity : categoryEntities) {
				if (orderDetailsEntity.getCategoryId().equals(categoryEntity.getCategoryId())) {
					summary[orderDetailsEntity.getCategoryId()][1] += orderDetailsEntity.getAmount()
							* orderDetailsEntity.getPrice();
					
				}
			}
		}
		//將資料回傳
				for (CategoryEntity categoryEntity : categoryEntities) {
					CategoryPriceDto categoryDto = new CategoryPriceDto();
					categoryDto.setPrice(summary[categoryEntity.getCategoryId()][1]);
					categoryDto.setCategoryName(categoryEntity.getCategoryName());
					result.add(categoryDto);
				}
			
			return result;
		}
	}

