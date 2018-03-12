package com.situ.mall.portal.vo;

import com.situ.mall.entity.Product;

public class CartItemVo {
	//购物车中productId对应的商品
    private  Product product; 
    //购买商品的数量
    private Integer amount;
    //当前商品是否被选中:0--未选中，1---选中
    private Integer isChecked;
	public Product getProduct() {
		return product;
	}
	
	public CartItemVo(Product product, Integer amount, Integer isChecked) {
		super();
		this.product = product;
		this.amount = amount;
		this.isChecked = isChecked;
	}


	public CartItemVo() {
		super();
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}
	@Override
	public String toString() {
		return "CartItem [product=" + product + ", amount=" + amount + ", isChecked=" + isChecked + "]";
	}
    
}
