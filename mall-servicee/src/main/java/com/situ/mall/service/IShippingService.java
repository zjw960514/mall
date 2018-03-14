package com.situ.mall.service;

import java.util.List;

import com.situ.mall.entity.Order;
import com.situ.mall.entity.Shipping;

public interface IShippingService {

	List<Shipping> selectByUserId(Integer id);

}
