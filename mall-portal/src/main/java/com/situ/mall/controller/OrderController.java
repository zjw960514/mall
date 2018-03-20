package com.situ.mall.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.situ.mall.constant.Const;
import com.situ.mall.entity.Order;
import com.situ.mall.entity.OrderItem;
import com.situ.mall.entity.Product;
import com.situ.mall.entity.Shipping;
import com.situ.mall.entity.User;
import com.situ.mall.portal.vo.CartItemVo;
import com.situ.mall.portal.vo.CartVo;
import com.situ.mall.response.ServerResponse;
import com.situ.mall.service.IOrderItemService;
import com.situ.mall.service.IOrderService;
import com.situ.mall.service.IProductService;
import com.situ.mall.service.IShippingService;
import com.situ.mall.service.impl.OrderServiceImpl;

@Controller
@RequestMapping("/order")
public class OrderController {
	

	private String CART_COOKIE = "cart_cookie";
	
	@Autowired
	IShippingService shippingService;
	
	@Autowired
	IProductService productService;
	
	@Autowired
	IOrderService orderService;
	
	@Autowired
	IOrderItemService orderItemService;
	
	
	@RequestMapping("/getOrderPage")
	public String getOrderPage(HttpSession session, Model model, HttpServletRequest request) {
		// 1.从Session中获得user对象
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		// 2.通过user得到收获地址
		List<Shipping> shippings = shippingService.selectByUserId(user.getId());
		model.addAttribute("shippings", shippings);
		// 3.将Cookie里面的购物车信息转换为CartVo对象
		CartVo cartVoFromCookie = getCartVoFromCookie(request);
		// 获得cookie之后要进行筛选，因为有没有勾选的物品选项，也就是说这些物品是不需要结算的，
		// 因此，在订单生成的这个时候要剔除，只展示给用户勾选的，也就是需要结算的选项。
		// 剔除list时候不能用for循环遍历，要用迭代器进行删除。
		List<CartItemVo> cartItemVos = cartVoFromCookie.getCartItemVos();
		Iterator<CartItemVo> iterator = cartItemVos.iterator();
		while (iterator.hasNext()) {
			CartItemVo CartItemVo = iterator.next();
			if (CartItemVo.getIsChecked() == Const.CartChecked.UNCHECKED) {
				iterator.remove();
			}else{
				//将没有勾选的商品删除之后，将剩下的商品刷新放进cartItem中
				Product product = productService.selectById(CartItemVo.getProduct().getId());
				CartItemVo.setProduct(product);
			}
		}
		model.addAttribute("cartVo", cartVoFromCookie);
		return "order";
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
	
	@RequestMapping("/addOrder")
	@ResponseBody
	public ServerResponse addOrder(
			Integer shippingId , BigDecimal actualPrice , 
			HttpServletRequest Request ,  
			Integer paytype,Integer postage,
			HttpSession session ,HttpServletResponse response){
		System.out.println(shippingId);
		System.out.println(actualPrice);
		//1、创建订单对象
		Order order = new Order();
		Long cuzz = System.currentTimeMillis();
		SimpleDateFormat format = 
				new SimpleDateFormat("yyyyMMddHHmmss");
		Date data =new Date(cuzz);
		order.setShippingId(shippingId);
		order.setOrderNo(Long.parseLong(format.format(data)));
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		order.setUserId(user.getId());
		order.setShippingId(shippingId);
		order.setPaymentType(paytype);
		order.setPayment(actualPrice);
		order.setPostage(postage);
		order.setSendTime(new Date());
		order.setCreateTime(new Date());
		order.setEndTime(new Date());
		//2、将订单插入数据库
		orderService.add(order);
		//3、从cookie中得到购物车CartVo
		CartVo cartVo = getCartVoFromCookie(Request);
		List<CartItemVo> cartItemVos = cartVo.getCartItemVos();
		for (CartItemVo cartItemVo : cartItemVos) {
			//购物车中被选中的才加入数据库
			if (cartItemVo.getIsChecked() == Const.CartChecked.CHECKED) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderNo(orderItem.getOrderNo());
				orderItem.setUserId(user.getId());
				Product product = productService.selectById(cartItemVo.getProduct().getId());
				orderItem.setProductId(product.getId());
				orderItem.setProductName(product.getName());
				orderItem.setProductImage(product.getMainImage());
				orderItem.setCurrentUnitPrice(product.getPrice());
				orderItem.setQuantity(cartItemVo.getAmount());
				orderItem.setTotalPrice(actualPrice);
				orderItem.setCreateTime(new Date());
				orderItem.setUpdateTime(new Date());
				orderItemService.addOrderItem(orderItem);
			}
		}
		//4.遍历cartVo将所有的isChecked是1的删除，
		//然后重新写会cookie(因为这个时候，之前那一些勾选的，
		//已经进订单了,所以要刷新cookie，移除的时候注意用迭代器完成)
		 Iterator<CartItemVo> iterator = cartItemVos.iterator();
		while (iterator.hasNext()) {
			CartItemVo cartItemVo = iterator.next();
				if ( cartItemVo.getIsChecked() == Const.CartChecked.CHECKED) {
				iterator.remove();
			}
		}
		Ccommon.setCartVoToCookie(response,cartVo);
		return ServerResponse.createSuccess("订单完成");
	}
	@RequestMapping("/getOrderInfo")
	public String getOrderInfo(HttpSession session,Model model){
		User  user = (User) session.getAttribute(Const.CURRENT_USER);
		model.addAttribute("user",user);
		List<Order> list = orderService.selectByUserId(user.getId());
		model.addAttribute("list",list);
		return "pay" ;
	}
	
	
}
