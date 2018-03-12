package com.situ.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.mall.entity.User;
import com.situ.mall.mapper.UserMapper;
import com.situ.mall.response.ServerResponse;
import com.situ.mall.service.IUserService;
import com.situ.mall.util.MD5Util;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public ServerResponse<User> login(String username, String password) {
		int resultCount = userMapper.checkUsername(username);
		if (resultCount == 0) {
			return ServerResponse.createError("用户名不存在");
		}
		String md5Password = MD5Util.EncodeUtf8(password);
		// User user = userMapper.selectLogin(username, md5Password);
		User user = userMapper.selectLogin(username, password);
		if (user == null) {
			return ServerResponse.createError("密码错误");
		}

		// user.setPassword(StringUtils.EMPTY);
		user.setPassword("");
		return ServerResponse.createSuccess("登录成功", user);
	}

	@Override
	public ServerResponse<List<User>> pageList(Integer page, Integer limit, User user) {
		// 1.设置分页
		PageHelper.startPage(page, limit);
		// 2、执行查询(分页之后的数据)
		List<User> list = userMapper.pageList(user);
		// 3、得到count
		//第一种：分页时，实际返回的结果list类型是Page<T>,如果想取出分页信息，需要强制转换为Page<T>
		//Integer count = (int) ((Page) list).getTotal();
		//用PageInfo对结果进行包装
		PageInfo pageInfo = new PageInfo<>(list);
		Integer count = (int) pageInfo.getTotal();
		return ServerResponse.createSuccess("查询成功", count, list);
	}

}
