package com.situ.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.entity.Product;
import com.situ.mall.response.ServerResponse;
import com.situ.mall.service.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private IProductService productService;
	
	@RequestMapping("/pageList")
	@ResponseBody
	public ServerResponse<List<Product>> pageList(Integer page, Integer limit, Product product) {
		return productService.pageList(page, limit, product);
	}
	
	@RequestMapping("/deleteByid")
	@ResponseBody
	public ServerResponse deleteByid(Integer id) {
		return productService.deleteByPrimaryKey(id);
	}
	
	@RequestMapping("/deleteAll")
	@ResponseBody
	public ServerResponse deleteAll(String ids) {
		return productService.deleteAll(ids);
	}
	
	
	@RequestMapping("/getProductPage")
	public String getProductPage(){
		return "product_list";
	}
	
	@RequestMapping("/getAddPage")
	public String getAddPage(){
		return "product_add";
	}
}
