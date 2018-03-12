package com.situ.mall.portal.vo;

import java.util.ArrayList;
import java.util.List;

public class CartVo {
	// 购物车里的商品集合
	private List<CartItemVo> cartItemVos = new ArrayList<>();

	public List<CartItemVo> getCartItemVos() {
		return cartItemVos;
	}

	public void setCartItemVos(List<CartItemVo> cartItemVos) {
		this.cartItemVos = cartItemVos;
	}

	public boolean addItem(CartItemVo cartItemVo , Integer stock) {
		boolean isExist = false;
		// 1、将cartVo里面商品的productId和amount插入cookie
		// 1.1如果这个商品cookie里面没有的话，就创建这个商品并插入到cookie中
		for (CartItemVo item : cartItemVos) {
			// 1.2如果cartItemVo这个商品在cookie当中已经存在了，根据productId找到这件商品，更新这个商品的amount
			 if ( cartItemVo.getProduct().getId().intValue() == item.getProduct().getId().intValue()){
				isExist = true;
				//这个商品新的数量 = 原来的商品数量  + 新添加的商品的数量
				int amount =  item.getAmount() + cartItemVo.getAmount();
				//判断商品数量有没有超过库存
				if (amount <= stock) {
					//如果数量没有超过库存，将数量更新到购物车中
					item.setAmount(amount);
					return true;
				}else {
					//如果数量超过库存，将最大库存数量更新到购物车中，并返回一个错误
					//item.setAmount(cartItemVo.getProduct().getStock());
					return false;
				}
			}
		}
		//在原来的购物车中没有这件商品，就在购物车中添加这件商品
		if (isExist == false) {
			cartItemVo.getProduct().setStock(null);
			cartItemVos.add(cartItemVo);
			return true;
		}
		return false;
	}
	
	

}
