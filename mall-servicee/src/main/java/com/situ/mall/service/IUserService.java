package com.situ.mall.service;

import java.util.List;

import com.situ.mall.entity.User;
import com.situ.mall.response.ServerResponse;

public interface IUserService {

	ServerResponse<User> login(String username, String password);

	ServerResponse<List<User>> pageList(Integer page, Integer limit, User user);

}
