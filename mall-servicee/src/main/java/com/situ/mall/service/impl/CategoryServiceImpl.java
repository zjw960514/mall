package com.situ.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.mall.entity.Category;
import com.situ.mall.entity.Product;
import com.situ.mall.mapper.CategoryMapper;
import com.situ.mall.response.ServerResponse;
import com.situ.mall.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	
	@Override
	public List<Category> selectTopCategoryList() {
		return  categoryMapper.selectTopCategory();
	}


	@Override
	public List<Category> selectSecondCategoryList() {
		return categoryMapper.selectSecondCategoryList();
	}





	@Override
	public ServerResponse selectTopCategory() {
		List<Category> list = categoryMapper.selectTopCategory();
		if (list == null || list.size() == 0) {
			return ServerResponse.createError("没有一级分类");
		}
		
		return ServerResponse.createSuccess("查找一级分类成功", list);
	}


	
	@Override
	public ServerResponse selectSecondCategory(Integer topCategoryId) {
		List<Category> list = categoryMapper.selectSecondCategory(topCategoryId);
		if (list == null || list.size() == 0) {
			return ServerResponse.createError("没有二级分类");
		}
		
		return ServerResponse.createSuccess("查找二级分类成功", list);
	}


	@Override
	public Integer selecPartentCategory(Integer categoryId) {
		 return categoryMapper.selecPartentCategory(categoryId);
	}


	@Override
	public Integer selectParentCategoryId(Integer categoryId) {
		/*return categoryMapper.selectByPrimarykKey(categoryId);*/
		return categoryMapper.selecPartentCategory(categoryId);
	}


	
}
