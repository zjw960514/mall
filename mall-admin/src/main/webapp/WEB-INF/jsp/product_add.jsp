<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>- 商品添加</title>
</head>
<body>
	<div>
		<form id="form_add" class="layui-form layui-form-pane" action="" method="post" enctype="multipart/form-data">
			<div class="layui-form-item">
				<label class="layui-form-label">商品名称</label>
				<div class="layui-input-block">
					<input type="text" name="name" autocomplete="off"
						placeholder="请输入商品名称" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">商品副标题</label>
				<div class="layui-input-block">
					<input type="text" name="username" lay-verify="required"
						placeholder="请输入商品副标题" autocomplete="off" class="layui-input">
				</div>
			</div>


			<div class="layui-form-item">
				<label class="layui-form-label">商品分类</label>
				<div class="layui-input-inline">
					<select name="quiz1" id="selecTopCategory" lay-filter="topCategoryFilter">
						<option value="">请选择一级分类</option>
					</select>
				</div>
				<div class="layui-input-inline">
					<select name="quiz2" id="secondCategory">
						<option value="">请选择二级分类</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">商品状态</label>
				<div class="layui-input-block">
					<input type="radio" name="sex" value="1" title="上架" checked="">
					<input type="radio" name="sex" value="2" title="下架">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">商品价格</label>
				<div class="layui-input-block">
					<input type="text" name="price" autocomplete="off"
						placeholder="请输入商品价格" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">商品库存</label>
				<div class="layui-input-block">
					<input type="text" name="price" autocomplete="off"
						placeholder="请输入商品库存" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">商品主图 </label>
				<div class="layui-input-block">
					<img alt="" src="" id="imgId" width="100" height="100"><br>
					<input type="file" name="pictureFile"/>
				</div>
			</div>	
			
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">文本域</label>
				<div class="layui-input-block">
					<textarea placeholder="请输入内容" class="layui-textarea"></textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<button class="layui-btn" lay-submit="" lay-filter="demo2" >跳转式提交</button>
			</div>
		</form>
	</div>
	<script>

		$(function(){
			//加载一级分类
			$.ajax({
						url : '${ctx}/category/selectTopCategory.action',
						type : "POST",
						dataType : "json",
						success : function(jsonObj) {
							console.log(jsonObj);
							if (jsonObj.code == util.SUCCESS) {
								var html = '<option value="">请选择一级分类</option>';
								var data = jsonObj.data;
								for (var i = 0; i <  data.length; i++) {
									html += '<option value="'+ data[i].id+'">'+ data[i].name + '</option>';
								}
								$('#selecTopCategory').html(html);
							} else {

							}
						}
					})
		}),
		
		//加载二级分类
		layui.use([ 'form' ], function() {
			var form = layui.form;
			form.render('select'); //刷新select选择框渲染,不然不显示
			form.on('select(topCategoryFilter)', function(data) {
				console.log(data.elem); //得到select原始DOM对象
				console.log(data.value); //得到被选中的值
				console.log(data.othis); //得到美化后的DOM对象
				$.ajax({
					url : '${ctx}/category/selectSecondCategory.action',
					data : 'topCategoryId=' + data.value,
					dataType : 'json',
					type : 'POST',
					success : function(jsonObj) {
						if (jsonObj.code == util.SUCCESS) {
							var html = '<option value="">请选二级分类</option>';
							var data = jsonObj.data;
							for (var i = 0; i < data.length; i++) {
								html += '<option value="'+data[i].id+'">'
										+ data[i].name + '</option>';
							}
							$('#secondCategory').html(html);
							form.render('select'); //刷新select选择框渲染,不然不显示
						} else {
							mylayer.errorMsg(jsonObj.msg);
						}
					}
				});
			});
		});
		
		//图片上传
		function uploadPic(){
			$('#form_add').ajaxSubmit({
				
				
			});
		}
	</script>
</body>
</html>