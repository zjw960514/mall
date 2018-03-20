package com.situ.mall.service;

import java.util.List;

import com.situ.mall.entity.Order;

public interface IOrderService {

	void add(Order order);

	List<Order> selectByUserId(Integer id);

}
