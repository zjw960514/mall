<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>- 商品列表</title>
</head>
<body>

	<div class="demoTable">
	  搜索ID：
	  <div class="layui-inline">
	    <input class="layui-input" name="id" id="searchName" autocomplete="off">
	  </div>
	  <button class="layui-btn" data-type="reload">搜索</button>
	</div>

	<script type="text/html" id="toolbar">
  		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
  		<a class="layui-btn  layui-btn-xs" lay-event="edit">编辑</a>
  		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
	
	<table class="layui-hide" id="dataGrid"></table>
	<script>
	layui.use('table', function(){
	  var table = layui.table;
	  
	  table.render({
	    elem: '#dataGrid',//绑定要渲染的表格
	    url:'${ctx}/user/pageList.action',//进行异步请求的地址
	    cellMinWidth:80,//列宽自动分配
	    cols: [[
			{field:'id',  title: 'ID', sort: true},
			{field:'username',  title: '用户名', sort: true},
			{field:'password',  title: '密码', sort: true},
			{field:'email',  title: '邮箱', sort: true},
			{field:'phone',  title: '手机', sort: true},
			{field:'question', title: '提示问题', sort: true},
			{field:'answer',  title: '答案', sort: true},
			{field:'role',  title: '角色', sort: true},
			{field:'create_time',  title: '创建时间', sort: true},
			{field:'update_time',  title: '更新时间', sort: true}
	    ]],
	     page: true 
	  });
	});
</script>
</body>
</html>