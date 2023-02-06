package com.ddt.finalproject.dto;

import java.util.List;

public class CategoryPriceDto {

	
	private String categoryName;
	
	private Integer price=0;
	private String percentage;

	private List<CategoryDetailsDto> orderList; 

	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = this.price+price;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public List<CategoryDetailsDto> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<CategoryDetailsDto> orderList) {

		this.orderList = orderList;
	}
	
	
}
