package com.situ.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	
	@RequestMapping("/getOrderPage")
	public String getOrderPage(){
		return "order";
	}
	
}
