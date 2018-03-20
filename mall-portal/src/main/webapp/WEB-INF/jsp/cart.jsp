<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>靓淘网_购物车</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/front/css/cart_style.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/front/css/login_style.css" />
	</head>

	<body>
		<div class="bg_color">
			<div class="top_center">
				<div class="left">
					<span class="wel">欢迎来到靓淘网！</span>
				</div>
				<div class="right">
					<ul>
						<li>
							<a class="login" href="login.html" target="_blank">请登录</a>
						</li>
						<li>
							<a href="register.html" target="_blank">快速注册</a>
						</li>
						<li>
							<a class="collect" href="">我的收藏</a>
						</li>
						<li>
							<a class="indent" href="">我的订单</a>
						</li>
						<li>
							<a class=phone href="">手机靓购</a>
						</li>
						<li>
							<a href="">我的积分</a>
						</li>
						<li>
							<a href="">我的评价</a>
						</li>
					</ul>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
		<div class="logo_center">
			<div class="left">
				<img class="logo_img" src="${ctx}/static/front/img/LOGO.png" />
				<span class="car_text">购物车</span>
			</div>
			<div class="right">
				<input class="car_input" type="text" />
				<input class="car_search" type="button" value="搜索" />
			</div>
		</div>
		<div class="title">
			<div class="title_top">
				<ul>
					<li>
						<a href="">全部商品</a>
					</li>
					<li>
						<a href="">降价商品</a>
					</li>
					<li>
						<a href="">库存紧张</a>
					</li>
				</ul>
				<p style="margin:0;padding-right: 10px; float: right;line-height: 40px;">配送至：<span style="border: 1px solid rgb(51,51,51);">山东省 青岛市 市南区 <img src="${ctx}/static/front/img/narrow.png"/></span></p>
			</div>
			<div class="title_center">
			</div>
			
		</div>
		<c:forEach items="${cartVo.cartItemVos}" var="cartItemVo">
				<div class="car_2">
					<ul>
						<li class="car_2_1"><input type="checkbox" style="color: #666666;margin: 23px 11px 10px 22px;" /></li>
						<li class="car_2_2">西边数码专营店</li>
						<li class="car_2_3"><img src="${ctx}/static/front/img/157.png" style="margin-left: 142px; " /></li>
					</ul>
				</div>
			<div class="car_1">
				<div class="car_1_top">
					<img src="${ctx}/static/front/img/158.png" />
					<p class="car_1_top_p">
						<span class="span1">
							活动商品购满¥105.00 , 即可加价换购商品1件&gt;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</span>
						<span class="span2">
							&nbsp;查看换购品
						</span>
						<span class="span3">
							&nbsp;&nbsp;去凑单&gt;
						</span>
					</p>
				</div>
				<div class="car_2_bottom"> 
					<div class="car_con_1"  onclick="selectProductStatus(${cartItemVo.product.id})">
						<c:if test="${cartItemVo.isChecked == 1}">
						<input checked  id="checkbox${cartItemVo.product.id}" name="selectCheckbox" type="checkbox" />
						</c:if>
						<c:if test="${cartItemVo.isChecked == 0}">
						<input  id="checkbox${cartItemVo.product.id}" name="selectCheckbox" type="checkbox" />
						</c:if>
					</div>
					<div class="car_con_2">
						<img src="/pic/57caddf7b86b40f9f879adc59c6c282f.jpg" style="width:60px" />
					</div>
					<div class="car_con_3">
						<p class="p_title">${cartItemVo.product.name}</p>
						<img src="${ctx}/static/front/img/160.png" />
						<p class="p_seven">&nbsp;支持7天无理由退货</p>
						<img src="${ctx}/static/front/img/161.png" />
						<p class="p_select">&nbsp;选包装</p>
					</div>
					<ul class="car_ul">
						<li class="price">
							<span style="color: #CCCCCC; margin-bottom: 15px;line-height: 20px;">
								<del>
									¥ 1699.00<br />
								</del>
							</span>
							<span style="color: #666666;" id="price${cartItemVo.product.id}"  price="${cartItemVo.product.price}">
								¥ ${cartItemVo.product.price}
							</span>
						</li>
						<li class="num_select">
							<input class="car_ul_btn1" type="button" value="-" onclick="addOrSub(${cartItemVo.product.id},'-')" />
							<input class="car_ul_text" type="text" id="num${cartItemVo.product.id}" placeholder="1" value="${cartItemVo.amount}"/>
							<input class="car_ul_btn2" type="button" value="+" onclick="addOrSub(${cartItemVo.product.id},'+')" />
						</li>
						<li class="money">
							<span style="color: #F41443;" id="cartItemTotalPrice${cartItemVo.product.id}">
								${cartItemVo.product.price*cartItemVo.amount} 
							</span>
						</li>
						<li class="delete">
							<img onclick="delCartItemById(${cartItemVo.product.id})" src="${ctx}/static/front/img/166.png" />
						</li>
					</ul>
				</div>
				<div class="clearfix"></div>
			</div>
		</c:forEach>                                                              
		
		<div class="blank">
		</div>
		<div class="total">
				<ul style="color: #666666;margin-top: 10px;margin-bottom: 10px;">
					<li style="margin-left: 16px;margin-right: 8px;">
						<input type="checkbox" id="selectAll" onclick="selectAll()" />
					</li>
					<li style="margin-left: 8px;margin-right: 265px;">全选</li>
					<li style="margin-left: 265px;margin-right: 18px;">总金额（已免运费）：<span id="totalPrice" style="color: #F41443;">¥</span></li>
					<li class="total_right"><a onclick="toAddOrder()" id="totalPrice">立即结算</a></li>
				</ul>
			</div>
					<div class="sp">
			<div class="sp1">
				<p style="color: rgb(51,51,51);">品质保障</p>
				<p style="font-size: 12px; color: #808080;margin-top: 5px;">品质护航 购物无忧</p>
			</div>
			<div class="sp2">
				<p style="color: rgb(51,51,51);">品质保障</p>
				<p style="font-size: 12px; color: #808080;margin-top: 5px;">品质护航 购物无忧</p>
			</div>
			<div class="sp3">
				<p style="color: rgb(51,51,51);">品质保障</p>
				<p style="font-size: 12px; color: #808080;margin-top: 5px;">品质护航 购物无忧</p>
			</div>
			<div class="sp4">
				<p style="color: rgb(51,51,51);">品质保障</p>
				<p style="font-size: 12px; color: #808080;margin-top: 5px;">品质护航 购物无忧</p>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="more">
			<div class="mb1">
				<ul>
					<li>
						购物指南
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;免费注册
						</a>
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;开通支付宝
						</a>
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;支付宝充值
						</a>
					</li>
				</ul>
			</div>
			<div class="mb2">
				<ul>
					<li>
						品质保障
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;发票保障
						</a>
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;售后规则
						</a>
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;缺货赔付
						</a>
					</li>
				</ul>
			</div>
			<div class="mb3">
				<ul>
					<li>
						支付方式
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;快捷支付
						</a>
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;信用卡
						</a>
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;货到付款
						</a>
					</li>
				</ul>
			</div>
			<div class="mb4">
				<ul>
					<li>
						商家服务
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;商家入驻
						</a>
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;商家中心
						</a>
					</li>
					<li>
						<a href="">
							&nbsp;&nbsp;运营服务
						</a>
					</li>
				</ul>
			</div>
			<div class="mb5">
				<ul>
					<li>
						手机靓淘
					</li>
					<li>
						<img src="${ctx}/static/front/img/98.png"/>
					</li>
				</ul>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="link">
			<ul>
				<li>
					<a href="">
						关于靓淘
					</a>
				</li>
				<li>
					<a href="">
						帮助中心
					</a>
				</li>
				<li>
					<a href="">
						开放平台
					</a>
				</li>
				<li>
					<a href="">
						诚聘精英
					</a>
				</li>
				<li>
					<a href="">
						联系我们
					</a>
				</li>
				<li>
					<a href="">
						网站合作
					</a>
				</li>
				<li>
					<a href="">
						法律声明及隐私政策
					</a>
				</li>
				<li>
					<a href="">
						知识产权
					</a>
				</li>
				<li>
					<a href="">
						廉政举报
					</a>
				</li>
				<li>
					<a href="">
						规则意见征集
					</a>
				</li>
			</ul>
		</div>
		<div class="copyright">
			COPYRIGHT 2010-2017 北京创锐文化传媒有限公司 JUMEI.COM 保留一切权利. 客服热线：400-123-888888<br /> 
			京公网安备 110101020011226|京ICP证111033号|食品流通许可证 SP1101051110165515（1-1）|营业执照
	</body>
		<!-- login弹出层 -->
			<div class="login" style="display:none" id="loginForm">
				<form id="login-form">
					<ul>
						<li class="login_title_1">
							<a href="">密码登录</a>
		
						</li>
						<li class="login_title_2">
							<a href="">扫码登录</a>
						</li>
						<li>
							<input id="username" name="username" class="login_user" type="text" placeholder="会员名/邮箱/手机号" />
							<input id="password" name="password" class="login_password" type="password" placeholder="密码" />
							<input class="login_btn" type="button" onclick="login()" value="登录" />
						</li>
						<li class="login_select">
							<a class="weibo" href="">微博登录</a>
							<a class="zhifubao" href="">支付宝登录</a><br />
						</li>
						<li class="renmenber_user">
							<input type="checkbox" value="remer_user" id="remer_user" />
							<label for="remer_user">记住用户名</label>
						</li>
						<li class="login_bottom">
							<a href="">忘记密码</a>
							<a href="">免费注册</a>
						</li>
					</ul>
				</form>
			</div>	
	<script type="text/javascript">
		layui.use(['layer'],function(){
			var layer = layui.layer;
		})
		
		$(function(){
			refreshTotalPrice();
		})
		
		//+ - 更新商品数量
		function addOrSub(productId,operator){
			var delta;
			if(operator == '+'){
				delta = 1;
			}else{
				delta = -1;
			}
			var num = $('#num'+productId).val();
			$.ajax({
				url:'${ctx}/cart/updateCart.shtml',
				data:{'productId':productId,'amount':delta},
				type:'POST',
				dataType:'json',
				success:function(jsonObj){
					if(jsonObj.code == util.SUCCESS){
						num =parseInt(num) + delta;
						$('#num'+productId).val(num);
						//updateTotalPrice
						var price = $('#price'+productId).attr('price');
						var totalPrice = num * price ;
						$('#cartItemTotalPrice' + productId).html(totalPrice);
						refreshTotalPrice();
					}else{
						mylayer.errorMsg(jsonObj.msg);
					}
				}
			})
		}
		
		function selectProductStatus(productId){
			var ischecked = $('#checkbox' + productId).prop('checked');
			$.ajax({
				url: '${ctx}/cart/addOrUpdateCart.shtml',
				data :{'productId':productId,'ischecked':ischecked},
				type:'POST',
				dataType:'json',
				success:function(jsonObj){
					if(jsonObj.code == util.SUCCESS){
						refreshTotalPrice();
					}else{
						mylayer.errorMsg(jsonObj.msg);
					}
				}
			})
		}
		
		function refreshTotalPrice(){
			var checkboxs = $('input[name=selectCheckbox]:checked');
			var  totalPrice = 0.00;
			for(var i = 0 ; i < checkboxs.length ; i++ ){
				//
				//checkbox268  substr截取需要的id
				var checkboxId =checkboxs[i].getAttribute('id');
				var id = checkboxId.substr('checkbox'.length);
				var cartItemTotalPrice = $('#cartItemTotalPrice'+ id).html();
				totalPrice += parseFloat(cartItemTotalPrice);
			}
			$('#totalPrice').html(totalPrice);
		}
		
		function delCartItemById(productId){
			layer.confirm('您确认要删除吗?',function(){
				$.ajax({
					url: '${ctx}/cart/delCartItemById.shtml',
					data:{'productId':productId},
					type:'POST',
					dataType: 'json',
					success : function(jsonObj){
						if(jsonObj.code == util.SUCCESS){
							mylayer.success(jsonObj.msg);
							//在demo页面中将CartItem项移除
							$('#checkbox'+ productId).parent().parent().parent().remove();
							refreshTotalPrice();
						}else{
							mylayer.errorMsg(jsonObj.msg);
						}	
					}
				})
			});
		}
		//跳转到结算页面
		function toAddOrder() {
			//todo:用户没有勾选任何商品checkbox，给用户一个提示：您还没有选择任何商品
			var user = '${CURRENT_USER}';
			if(user == '') {
				layer.open({
					type : 1,
					title : '登录',
					offset : '50px',
					area : ['350px','350px'],
					content : $('#loginForm')
				});
			} else {
				window.location.href = '${ctx}/order/getOrderPage.shtml';
			}
		}
		
		function login() {
    		/*
    		1、验证用户名
    		1.1、验证用户名是否为空
    		1.2、是否合法：4-8数字或字母
    		2、密码不能为空
    		3、ajax提交用户名和密码，并且接受后台返回的json数据
    		*/
    		var username = $("#username").val();
    		var password = $("#password").val();
    		//1.1、验证用户名是否为空
    		if(util.isNull(username)) {
    			mylayer.errorMsg("用户名不能为空");
    			return;
    		}
    		
    		//1.2、是否合法：4-8数字或字母
    		if(!isUsernameValid(username)) {
    			mylayer.errorMsg("用户名不合法，4-8数字或字母");
    			return;
    		}
    		
    		//2、密码不能为空
    		if(util.isNull(password)) {
    			mylayer.errorMsg("密码不能为空");
    			return;
    		}
    		
    		//3、ajax提交用户名和密码，并且接受后台返回的json数据
    		$.ajax({
    			url : "${ctx}/user/login.shtml",
    			type : "POST",
    			dataType : "json",
    			data : $("#login-form").serialize(),
    			success : function(data) {
    				if(data.code == util.SUCCESS) {
    					mylayer.success(data.msg);
    					//mylayer.successUrl(data.msg, "${ctx}/index.action");
    					window.location.href = '${ctx}/order/getOrderPage.shtml';
    				} else {
    					mylayer.errorMsg(data.msg);
    				}
    			}
    		});
    	}
    	
    	/*是否合法：4-8数字或字母*/
    	function isUsernameValid(value) {
    		var pattern = /^[0-9a-zA-Z]{4,8}$/;
    		return pattern.test(value);
    	} 
	</script>
</html>