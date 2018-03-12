package com.situ.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.situ.mall.entity.Product;
import com.situ.mall.service.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private IProductService productService;
	
	@RequestMapping("/getProductListPage")
	private String getProductListPage(Integer categoryId , Model model) {
		List<Product> productList = productService.getProductListPage(categoryId);
		model.addAttribute("productList",productList);
		return "product_list";
	}
	
	@RequestMapping("/getProductDetail")
	private String getProductDetail(Integer productId , Model model) {
		Product product = productService.getProductDetail(productId);
		System.out.println(product);
		model.addAttribute("product",product);
		return "product_detail";
	}
}
