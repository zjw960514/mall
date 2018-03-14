package com.situ.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.situ.mall.entity.Order;
import com.situ.mall.entity.Shipping;
import com.situ.mall.mapper.ShippingMapper;
import com.situ.mall.service.IShippingService;


@Service
public class ShippingServiceImpl implements IShippingService {
	
	@Autowired
	ShippingMapper shippingMapper;

	@Override
	public List<Shipping> selectByUserId(Integer id) {
		List<Shipping> selectByPrimaryKey = shippingMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

}
