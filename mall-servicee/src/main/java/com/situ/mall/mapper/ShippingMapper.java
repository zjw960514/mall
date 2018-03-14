package com.situ.mall.mapper;

import java.util.List;

import com.situ.mall.entity.Order;
import com.situ.mall.entity.Shipping;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    List<Shipping> selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}