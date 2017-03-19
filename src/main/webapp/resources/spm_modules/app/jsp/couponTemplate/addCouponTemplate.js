define('app/jsp/couponTemplate/addCouponTemplate', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("jquery-validation/1.15.1/jquery.validate");
	require("app/util/aiopt-validate-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var OrderListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 20
    	},
    	//事件代理
    	events: {
    		"blur #couponName":"_cheName",
    		"click #save":"_save"
        },
    	//重写父类
    	setup: function () {
    		var _this = this;
    		OrderListPager.superclass.setup.call(this);
    	},
		_save:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
			var maxCountIssue = $("#maxCountIssue").val();
			var noLimit = $("noLimit").val();
			var faceValue = $("#faceValue").val();
			var random = $("random").val();
			var couponUserId = $("#couponUserId").val();
			var sum = $("sum").val();
			var effectiveStartTime = $("effectiveStartTime").val();
			var day = $("day").val();
			if(maxCountIssue != null && noLimit != null){
				$("#maxCountOnly").html("发放数量二选一");
			}else if(faceValue != null && random != null){
				$("#faceValueOnly").html("面值二选一");
			}else if(couponUserId != null && sum != null){
				$("#couponUserOnly").html("使用规则二选一");
			}else if(effectiveStartTime != null && day != null){
				$("#effectiveOnly").html("有效期时间和天数二选一");
			}else{
				var param = $("#dataForm").serializeArray();
				ajaxController.ajax({
					type: "post",
					processing: true,
					message: "保存数据中，请等待...",
					url: _base + "/coupon/insertCouponTemplate",
					data: param,
					success: function (rs) {
						_this._cancel();
					}
				});
			}
		},
		_initValidate:function(){
	    	   var _this = this;
	    	   var formValidator = $("#dataForm").validate({
	    			rules: {
	    				"couponName":{
	    					required:true
	    				},
	    				"couponDesc": {
	    					maxlength:30
	    				},
	    				"maxCountIssue":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"currencyUnit":{
	    					required:true
	    				},
	    				"faceValue":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"useLimits":{
	    					required:true
	    				},
	    				"usedScene":{
	    					required:true
	    					/*required:"#selectFormatConv option[value=Y]:selected"*/
	    				},
	    				"couponUserId":{
	    					required:true
	    					/*required:"#selectFormatConv option[value=Y]:selected"*/
	    				},
	    				"receiveStartTime":{
	    				},
	    				"receiveEndTime":{
	    				},
	    				"effectiveStartTime":{
	    					required:true
	    				},
	    				"effectiveEndTime":{
	    					required:true
	    				},
	    				"status":{
	    					moneyNumber:true
	    				}
	    			},
	    			messages: {
	    				"couponName":{
	    					required:"请输入名称",
	    					maxlength:"联系人不能超过10字符",
	    					
	    				},
	    				"couponDesc": {
	    					maxlength:"描述不能超过30字符"
	    				},
	    				"maxCountIssue":{
	    					required:"请输入发放数量",
	    					number:"发放数量格式不正确",
	    					min:"发放数量不合法"
	    				},
	    				"currencyUnit":{
	    					required:"请选择币种单位"
	    				},
	    				"faceValue":{
	    					required:"请输入面值",
	    					number:"面值格式不正确",
	    					min:"面值不合法"
	    				},
	    				"useLimits":{
	    					required:"请选择使用限制"
	    				},
	    				"usedScene":{
	    					required:"请选择使用场景"
	    				},
	    				"couponUserId":{
	    					required:"请选择使用规则"
	    				},
	    				"effectiveStartTime":{
	    					required:"请选择有效期时间"
	    				},
	    				"effectiveEndTime":{
	    					required:"请选择有效期时间"
	    				},
	    			}
	    		});
	    	   return formValidator ;
	    	},
	    	_cheName:function(){
				var _this = this;
				var couponName = $("#couponName").val();
				if(couponName != "" && couponName != null){
					$.ajax({
						type: "post",
						data: {
							couponName,
						},
						url: _base + "/coupon/checkName",
						success: function (data) {
							if(data == 1){
								$(".cname").html("名称已存在");
								return false;
							}else{
								$(".cname").html("");
							}
						}
					});
				}
			}
    });
    
    module.exports = OrderListPager
});

