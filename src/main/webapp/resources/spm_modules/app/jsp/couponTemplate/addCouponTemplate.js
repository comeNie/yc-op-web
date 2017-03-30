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
    		"click #save":"_save",
    		"click #all":"_allCheck",
    		"click #add-close":"_closeDialog"
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
			var param = $("#dataForm").serializeArray();
			var f = {};//声明一个对象
			
				var couponName = $("#couponName").val();
				f["couponName"] =couponName;
				var couponDesc = $("#couponDesc").val();
				f["couponDesc"] =couponDesc;
				
				var maxCountIssue = $("#maxCountIssue").val();
				f["maxCountIssue"] =maxCountIssue;
				var currencyUnit = $("#currencyUnit").val();
				f["currencyUnit"] =currencyUnit;
				if($("#random").is(':checked')){
					var n1=Math.floor(Math.random()*10+1);
					f["faceValue"] = n1;
				}else{
					var faceValue = $("#faceValue").val();
					f["faceValue"] =faceValue;
				}
				var useLimits = $("#useLimits").val();
				f["useLimits"] =useLimits;
				var requiredMoneyAmount = $("#requiredMoneyAmount").val();
				f["requiredMoneyAmount"] =requiredMoneyAmount;
				var couponUserId = $("#couponUserId").val();
				f["couponUserId"] =couponUserId;
				var receiveStartTime=$("#receiveStartTime").val();
				var receiveEndTime=$("#receiveEndTime").val();
				f["receiveStartTime"] =receiveStartTime;
				f["receiveEndTime"] =receiveEndTime;
				if($("#day").is(':checked')){
					var effectiveTime = $("#effectiveTime").val();
					f["effectiveTime"] =effectiveTime;
				}else{
					var effectiveStartTime=$("#effectiveStartTime").val();
					var effectiveEndTime=$("#effectiveEndTime").val();
					f["effectiveStartTime"] =effectiveStartTime;
					f["effectiveEndTime"] =effectiveEndTime;
				}
				var status = $('input[name="status"]:checked').val();
				f["status"] =status;
				
				var usedScenes=[];
				$("input[name=usedScene]:checked").each(function(){
					usedScenes.push($(this).val());
				});
				var usedScenes = usedScenes;
				f["usedScene"] = usedScenes.join(",");
			//等遍历结束，就会生成一个json对象了

			//如果需要对象与字符串的转换
			//这是从json对象 向 json 字符串转换
			 var str = JSON.stringify(f);
			
			var url = _base + "/coupon/insertCouponTemplate";
			ajaxController.ajax({
				type: "post",
				dataType:"json",
				processing: true,
				message: "保存数据中，请等待...",
				url: url,
				data: f,
				success: function (rs) {
					window.history.back(-1); 
				}
			});
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
	    				},
	    				"effectiveEndTime":{
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
	    				}
	    			}
	    		});
	    	   return formValidator ;
	    	},
	    	_closeDialog:function(){
	    		window.location.href = _base+"/coupon/toCouponTemplateList";
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
							if(data >= 1){
								$(".cname").html("名称已存在");
								return false;
							}else{
								$(".cname").html("");
							}
						}
					});
				}
			},
			_allCheck:function(){  
				$("input[name='usedScene']").each( function() { 
					$(this).attr("checked", true); 
				}); 
			}
			
    });
    
    module.exports = OrderListPager
});

