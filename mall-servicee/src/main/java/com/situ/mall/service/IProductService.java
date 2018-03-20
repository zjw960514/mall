package com.situ.mall.service;

import java.util.List;

import com.situ.mall.entity.Product;
import com.situ.mall.response.ServerResponse;

public interface IProductService {

	ServerResponse<List<Product>> pageList(Integer page, Integer limit, Product product);

	ServerResponse deleteByPrimaryKey(Integer id);

	ServerResponse deleteAll(String ids);

	List<Product> getProductListPage(Integer categoryId);

	Product getProductDetail(Integer productId);

	Product selectById(Integer id);

	ServerResponse add(Product product);

}
