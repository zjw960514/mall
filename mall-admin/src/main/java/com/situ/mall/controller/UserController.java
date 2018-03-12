package com.situ.mall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.constant.Const;
import com.situ.mall.entity.User;
import com.situ.mall.response.ServerResponse;
import com.situ.mall.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/getLoginPage")
	public String getLoginPage() {
		return "login";
	}
	
	@RequestMapping("/getUserPage")
	public String getUserPage() {
		return "user_list";
	}
	
	@RequestMapping("/pageList")
	@ResponseBody
	public ServerResponse<List<User>> pageList(Integer page,Integer limit ,User user) {
		return userService.pageList( page , limit , user);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ServerResponse login(String username, String password, HttpSession session) {
		ServerResponse<User> response = userService.login(username, password);
		if (response.isSuccess()) {
			User user = response.getData();
			if (user.getRole() == Const.Role.ROLE_ADMIN) {
				//说明登录的确实是管理员
				session.setAttribute(Const.CURRENT_USER, user);
				return response;
			} else {
				return ServerResponse.createError("不是管理员，无法登录");
			}
		}
		return response;
	}
}
