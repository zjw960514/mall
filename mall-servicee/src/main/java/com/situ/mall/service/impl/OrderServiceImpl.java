package com.situ.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.mall.entity.Order;
import com.situ.mall.mapper.OrderMapper;
import com.situ.mall.service.IOrderService;


@Service
public class OrderServiceImpl implements IOrderService  {

	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public void add(Order order) {
		orderMapper.insertSelective(order);
	}

	@Override
	public List<Order> selectByUserId(Integer id) {
		 return orderMapper.selectByPrimarykKey(id);
	}
	
}
