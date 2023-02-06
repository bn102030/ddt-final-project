package com.ddt.finalproject.service;

import java.util.List;

import com.ddt.finalproject.dto.CategoryDto;
import com.ddt.finalproject.dto.CategoryPriceDto;


public interface CategoryService {
	
	List<CategoryPriceDto> getCategoryDataList();
	
}
