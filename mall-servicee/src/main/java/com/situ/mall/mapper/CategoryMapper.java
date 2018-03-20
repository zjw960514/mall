package com.situ.mall.mapper;

import java.util.List;

import com.situ.mall.entity.Category;
import com.situ.mall.entity.Product;
import com.situ.mall.response.ServerResponse;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

	List<Category> selectTopCategory();

	List<Category> selectSecondCategory();

	Integer selecPartentCategory(Integer categoryId);

	List<Category> selectSecondCategoryList();

	List<Category> selectSecondCategory(Integer topCategoryId);

	Integer selectByPrimarykKey(Integer category);

}