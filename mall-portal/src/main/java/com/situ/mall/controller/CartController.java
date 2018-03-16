package com.situ.mall.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.situ.mall.constant.Const;
import com.situ.mall.entity.Product;
import com.situ.mall.portal.vo.CartItemVo;
import com.situ.mall.portal.vo.CartVo;
import com.situ.mall.response.ServerResponse;
import com.situ.mall.service.IProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

	private String CART_COOKIE = "cart_cookie";

	@Autowired
	private IProductService productService;

	@RequestMapping("/getCartPage")
	public String getCartPage(HttpServletRequest request, Model model) {
		CartVo cartVo = getCartVoFromCookie(request);

		// 2、将cookie里面所有的商品通过商品的ID将该商品的所有信息查询出来，
		if (cartVo != null) {
			// 得到信息之后更新购物车中的商品信息，然后转换成Cart这个java对象
			List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
			for (CartItemVo item : cartItemVos) {
				Product product = productService.selectById(item.getProduct().getId());
				item.setProduct(product);
			}

		}

		model.addAttribute("cartVo", cartVo);

		return "cart";
	}

	/*
	 * 将Cookie中购物车信息转换为CartVo对象
	 * 
	 */
	private CartVo getCartVoFromCookie(HttpServletRequest request) {
		CartVo cartVo = null;
		// 将客户端中购物车cookie拿出来
		// springmvc里的一个工具，可以把cookie取出来
		ObjectMapper objectMapper = new ObjectMapper();
		// 只有对象不为null的才转换并展示
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		Cookie[] cookies = request.getCookies();
		// 判断cookie是否为空，不为空就遍历cookie，得到cookie中各个属性对应的的value值
		if (null != cookies && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				// 如果cookie中的有一项是cart_cookie(也就是cookie中被定义的购物车的名字),
				// 那么就取出这个属性cart_cookie对应的value值
				if (CART_COOKIE.equals(cookie.getName())) {
					String value = cookie.getValue();
					try {
						// 到下面这一步，cart_cookie对应的value值(是一个字符串)
						// 转换为了java对象cartVo(整个购物车)，也就是完成了json转换为java对象的操作
						cartVo = objectMapper.readValue(value, CartVo.class);
					} catch (JsonParseException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return cartVo;
	}

	@RequestMapping("/updateCart")
	@ResponseBody
	public ServerResponse updateCart(Integer productId, Integer amount, Boolean ischecked, HttpServletRequest request,
			HttpServletResponse response) {
		// 讲Cookie里面的购物车转换为CartVo对象
		CartVo cartVo = getCartVoFromCookie(request);
		// 原来cookie中没有购物车，所以转换为的CartVo是null。
		if (cartVo == null) {
			cartVo = new CartVo();
		}

		boolean result = addOrUpdateCartVo(productId, ischecked, amount, cartVo);
		if (result == false) {
			return ServerResponse.createError("添加购物车失败");
		}

		// 将CartVo对象设置到Cookie中
		setCartVoToCookie(response, cartVo);
		return ServerResponse.createSuccess("添加购物车成功");
	}

	@RequestMapping("/addOrUpdateCart")
	@ResponseBody
	public ServerResponse addOrUpdateCart(Integer productId, Integer amount, Boolean ischecked,
			HttpServletRequest request, HttpServletResponse response) {
		// 将Cookie里面的购物车转换为CartVo对象
		CartVo cartVo = getCartVoFromCookie(request);
		// 原来cookie中没有购物车，所以转换为的CartVo是null。
		if (cartVo == null) {
			cartVo = new CartVo();
		}

		boolean result = addOrUpdateCartVo(productId, ischecked, amount, cartVo);
		if (result == false) {
			return ServerResponse.createError("添加购物车失败");
		}

		// 将CartVo对象设置到Cookie中
		setCartVoToCookie(response, cartVo);
		return ServerResponse.createSuccess("添加购物车成功");
	}
	@RequestMapping("/delCartItemById")
	@ResponseBody
	public ServerResponse delCartItemById(Integer productId , HttpServletResponse response, HttpServletRequest request) {
		// 将Cookie里面的购物车转换为CartVo对象
		CartVo cartVo = getCartVoFromCookie(request);
		// 原来cookie中没有购物车，所以转换为的CartVo是null。
		if (cartVo == null) {
			return ServerResponse.createError("获取购物车失败");
		}
		//遍历删除指定的id的购物项
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		Iterator<CartItemVo> iterator = cartItemVos.iterator();
		while (iterator.hasNext()) {
			CartItemVo cartItemVo = iterator.next();
			if (productId.intValue() == cartItemVo.getProduct().getId().intValue() ) {
				iterator.remove();
			}
		}
		// 将CartVo对象设置到Cookie中
		setCartVoToCookie(response, cartVo);
		return ServerResponse.createSuccess("删除购物车成功");
	
	}
	
	
	private boolean addOrUpdateCartVo(Integer productId, Boolean ischecked, Integer amount, CartVo cartVo) {
		Product productTemp = productService.selectById(productId);
		boolean isExist = false;
		// 1、将cartVo里面商品的productId和amount插入cookie
		// 1.1如果这个商品cookie里面没有的话，就创建这个商品并插入到cookie中
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		for (CartItemVo item : cartItemVos) {
			// 1.2如果cartItemVo这个商品在cookie当中已经存在了，根据productId找到这件商品，更新这个商品的amount
			if (item.getProduct().getId().intValue() == productId.intValue()) {
				isExist = true;
				if (amount != null) {
					// 这个商品新的数量 = 原来的商品数量 + 新添加的商品的数量
					int newAmount = item.getAmount() + amount;
					// 判断商品数量有没有超过库存
					if (newAmount > productTemp.getStock()) {
						// 如果数量超过库存，返回添加失败
						return false;
					}
					item.setAmount(newAmount);
				}
				if (ischecked != null) {
					if (ischecked) {
						item.setIsChecked(Const.CartChecked.CHECKED);
					} else {
						item.setIsChecked(Const.CartChecked.UNCHECKED);
					}
				}
				return true;
			}
		}
		// 在原来的购物车中没有这件商品，就在购物车中添加这件商品
		if (isExist == false) {
			CartItemVo cartItemVo = new CartItemVo();
			Product product = new Product();
			product.setId(productId);
			cartItemVo.setProduct(product);
			cartItemVo.setIsChecked(Const.CartChecked.CHECKED);
			cartItemVo.setAmount(amount);
			cartItemVos.add(cartItemVo);
			return true;
		}
		return false;
	}

	/*
	 * 将CartVo对象中购物车信息设置到Cookie中
	 * 
	 */
	private void setCartVoToCookie(HttpServletResponse response, CartVo cartVo) {
		ObjectMapper objectMapper = new ObjectMapper();
		// 只有对象不为null的才转换并展示
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 将java形式的cartVo对象转换成json形式的key对应的value值(这个value值是一个字符串)，
		// 并放回到cookie中
		StringWriter stringWriter = new StringWriter();
		try {
			objectMapper.writeValue(stringWriter, cartVo);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 将构造的json的key值---CART_COOKIE放到cookie里面
		Cookie cookie = new Cookie(CART_COOKIE, stringWriter.toString());
		// 设置cookie的存储时间
		cookie.setMaxAge(24 * 60 * 60);
		// 设置cookie的路径
		cookie.setPath("/");
		// 将cookie发送到浏览器
		response.addCookie(cookie);
	}

}
