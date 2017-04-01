define('app/jsp/user/userdetails', function(require, exports, module) {
	'use strict';
	var $ = require('jquery');
	var Widget = require('arale-widget/1.2.0/widget');
	var Dialog = require("optDialog/src/dialog");
	var AjaxController = require('opt-ajax/1.0.0/index');
	require("jquery-validation/1.15.1/jquery.validate");
	require("app/util/aiopt-validate-ext");
	require("jsviews/jsrender.min");
	require("jsviews/jsviews.min");
	require("app/util/jsviews-ext");
	require("app/jsp/order/jsviews-order");
	require("webuploader/webuploader");
	require("webuploader/webuploader2.css");

	var ajaxController = new AjaxController();
	
	var cache = null;
	
	var showErrorDialog = function(error){
    	var d = Dialog({
			content:error,
			icon:'fail',
			okValue: '确 定',
			title: '提示',
			ok:function(){
				this.close();
			}
		});
		d.showModal();
    }
	
	var showSuccessDialog = function(error){
    	var d = Dialog({
			content:error,
			icon:'success',
			okValue: '确 定',
			title: '提示',
			ok:function(){
				this.close();
		    	window.location.href =_base+"/user/toUserList";
			}
		});
		d.showModal();
    }
	var showSuccessDialog1 = function(error){
    	var d = Dialog({
			content:error,
			icon:'success',
			okValue: '确 定',
			title: '提示',
			ok:function(){
				this.close();
		    	window.location.href =_base+"/user/toUserDetail?userId="+$("#userId").val();;
			}
		});
		d.showModal();
    }
	
    
	var userDetailsPager = Widget.extend({

		attrs : {
			
		},
		Statics : {
			
		},
		// 事件代理
		events : {
			"click #save":"_save",
			"click #lock":"_lock"
		},
		// 重写父类
		setup : function() {
			var _this = this;
			userDetailsPager.superclass.setup.call(this);
			
			//日期控件
			$(document).on("click",".fa-calendar",function(){
				var calInput = $(this).parent().prev();
				var timeId = calInput.attr('id');
				WdatePicker({el:timeId,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});
			});
			
			//下载事件
			$(document).on("click",".download",function(){
				var parent = $(this).parent();
				var fileId = parent.find("input").eq(0).val();
				var fileName = parent.find("input").eq(1).val();
				var ext = fileName.substring(fileName.indexOf('.')+1);
				//fileName = window.encodeURI(window.encodeURI(fileName));
				fileName=encodeURIComponent(encodeURIComponent(fileName));
				location.href = _base + '/attachment/download?fileId='+fileId+'&fileName='+fileName;
			});
			this._queryUserDetails();
			
			var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
			
		},
		_save:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#userForm").valid()){
				return false;
			}
			var f = {};
			f.email = $('[name=email]').val();
			f.userId = $('#userId').val();
			f.nickname = $('[name=nickname]').val();
			f.username = $('[name=username]').val();
			f.name = $('[name=name]').val();
			f.sex = $('[name=sex]').val();
			f.mobilePhone = $('[name=mobilePhone]').val();
			
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "保存数据中，请等待...",
				url: _base + "/user/updateUserDetail",
				data: f,
				success: function (rs) {
					if(rs.statusCode=="1"){
						showSuccessDialog(rs.statusInfo);
					}else{
						showErrorDialog(rs.statusInfo);
					}
				}
			});
		},
		_queryUserDetails:function(){
			var _this = this;
			var param = {};
			param.userId = $("#userId").val();
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "加载数据中，请等待...",
				url: _base + "/user/userDetail",
				data: param,
				success: function (rs) {
					cache = rs;
					_this._initView(rs);
				}
			    
			});
		},
		_initView:function(rs){
			
			var _this = this;
			var model = $("#mod").val();
			rs.data.mod = model;
			var userInfoHtml = $("#userInfoTempl").render(rs.data);
			console.log(userInfoHtml);
			$("#date1").html(userInfoHtml);

			var growthValueHtml = $("#growthValueTempl").render(rs.data);
			console.log(growthValueHtml);
			$("#growthValueTable").html(growthValueHtml);
			
			if(null != rs.data.usrUser.sex){
				$("[name=sex]").val(rs.data.usrUser.sex);
			}
			if(null != rs.data.usrUser.state){
				$("#state").val(rs.data.usrUser.state);
			}
		},
       _initValidate:function(){
    	   var _this = this;
    	   var formValidator = $("#userForm").validate({
    			rules: {
    				"nickname":{
    					required:true,
    					maxlength:10,
    					minlength:2
    				},
    				"username": {
    					required:true,
    					maxlength:20,
    					minlength:2
    				},
    				"name":{
    					required:true,
    					maxlength:6,
    					minlength:2
    				},
    				"sex":{
    					required:true
    				},
    				"mobilePhone":{
    					required:true,
    					number:true,
    					rangelength:[11,11]
    				},
    				"userId":{
    					required:true
    				},
    				"email":{
    					required:true,
    					email:true,
    					maxlength:64
    				}
    			},
    			messages: {
    				"nickname":{
    					required:"请输入昵称",
    					maxlength:"昵称不能超过10字符",
    					minlength:"昵称长度不能少于2个字符"
    				},
    				"username": {
    					required:"请输入用户名",
    					maxlength:"用户名不能超过20字符",
    					minlength:"用户名长度不能少于6个字符"
    				},
    				"name":{
    					required:"请输入姓名",
    					maxlength:"姓名不能超过6字符",
    					minlength:"用户名长度不能少于2个字符"
    				},
    				"sex":{
    					required:"请选择性别"
    				},
    				"mobilePhone":{
    					required:"请输入手机号",
    					rangelength:"手机号格式不正确"
    				},
    				"userId":{
    					required:"请输入用户Id"
    				},
    				"email":{
    					required:"请输入转化格式",
    					email:"邮箱格式不正确",
        				maxlength:"邮箱不能超过64字符"
    				}
    			}
    		});
    		
    	   return formValidator ;
    	},
    	_lock:function(){
    		
    		var k ={};
    		k.userId = $("#userId").val();
    		var state = $("#state").val();
    		if(state==1 || state ==0){
    			k.state = 2;
    		}else{
    			k.state = 0;
    		}
    		
    		ajaxController.ajax({
				type: "post",
				processing: true,
				message: "保存数据中，请等待...",
				url: _base + "/user/updateUserDetail",
				data: k,
				success: function (rs) {
					if(rs.statusCode=="1"){
						showSuccessDialog1(rs.statusInfo);
					}else{
						showErrorDialog(rs.statusInfo);
					}
				}
			});
    	}
	});
	module.exports = userDetailsPager;
});