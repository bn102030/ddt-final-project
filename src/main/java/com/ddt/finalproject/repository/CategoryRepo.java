package com.ddt.finalproject.repository;

import com.ddt.finalproject.entity.CategoryEntity;
import com.ddt.finalproject.entity.OrderDetailsEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {
	
}
