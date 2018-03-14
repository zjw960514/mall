package com.situ.mall.controller;

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

	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ServerResponse login(String username, String password, HttpSession session) {
		ServerResponse<User> response = userService.login(username, password);
		User user = response.getData();
		session.setAttribute(Const.CURRENT_USER, user);
		return response;
	}
}
