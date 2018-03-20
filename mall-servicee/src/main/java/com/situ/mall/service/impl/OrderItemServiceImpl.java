package com.situ.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.mall.entity.OrderItem;
import com.situ.mall.mapper.OrderItemMapper;
import com.situ.mall.service.IOrderItemService;

@Service
public class OrderItemServiceImpl implements IOrderItemService {
	
	@Autowired
	private OrderItemMapper orderItemMapper ;

	@Override
	public void addOrderItem(OrderItem orderItem) {
		orderItemMapper.insert(orderItem);
	}

	
	
	
	
	
}
