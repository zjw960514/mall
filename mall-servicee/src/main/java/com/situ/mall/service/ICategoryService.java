package com.situ.mall.service;

import java.util.List;

import com.situ.mall.entity.Category;
import com.situ.mall.entity.Product;
import com.situ.mall.response.ServerResponse;

public interface ICategoryService {

	ServerResponse selectTopCategory();

	ServerResponse selectSecondCategory(Integer topCategoryId);
	
	Integer selecPartentCategory(Integer categoryId);
	
	List<Category> selectTopCategoryList();

	List<Category> selectSecondCategoryList();

	Integer selectParentCategoryId(Integer categoryId);
	
}
