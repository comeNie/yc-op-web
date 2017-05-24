define('app/jsp/sysconfig/sysConfigList', function (require, exports, module) {
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
			//保存
    		"click #save":"_save"
        },
    	//重写父类
    	setup: function () {
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
			var url = _base + "/sysconfig/updateSysConfig";
			ajaxController.ajax({
				type: "post",
				dataType:"json",
				processing: true,
				message: "保存数据中，请等待...",
				url: url,
				data: param,
				success: function (rs) {
					window.location.reload();
				}
			});
		},
		_initValidate:function(){
	    	   var _this = this;
	    	   var formValidator = $("#dataForm").validate({
	    			rules: {
	    				"ordinaryMember":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"goldMember":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"platinumMember":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"masonryMember":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"capValue":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"v1Points":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"v2Points":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"v3Points":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"lspPoints":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"lgdateNum":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"customNum":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"interpreterNum":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"orderNum":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"languageNum":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"lstate":{
	    					required:true
	    				},
	    				"oneDay":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"twoDay":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"threeDay":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"fourDay":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"fiveDay":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"sixDay":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"sevenDay":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"regIntegration":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"rstate":{
	    					required:true
	    				},
	    				"regGrowth":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"gstate":{
	    					required:true
	    				},
	    				"inviteIntegrati":{
	    					required:true,
	    					digits:true,
	    					min:0
	    				},
	    				"istate":{
	    					required:true
	    				},
	    				"pcNotice":{
	    					required:true,
	    					maxlength:100
	    				},
	    				"wapNotice":{
	    					required:true,
	    					maxlength:100
	    				}
	    			},
	    			messages: {
	    				"ordinayryMember":{
	    					required:"普通会员不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"goldMember":{
	    					required:"黄金会员不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"platinumMember":{
	    					required:"白金会员不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"masonryMember":{
	    					required:"钻石会员不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"capValue":{
	    					required:"封顶值不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"v1Points":{
	    					required:"v1译员不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				
	    				"v2Points":{
	    					required:"v2译员不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"v3Points":{
	    					number:"v3译员不能为空",
	    					min:"数值需大于0"
	    				},
	    				"lspPoints":{
	    					required:"lsp译员不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"lgdateNum":{
	    					required:"语料不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"customNum":{
	    					required:"客户不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"interpreterNum":{
	    					required:"译员不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"orderNum":{
	    					required:"订单不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"languageNum":{
	    					required:"语种不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"lstate":{
	    					required:"请选择状态"
	    				},
	    				"oneDay":{
	    					required:"登录1日不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"twoDay":{
	    					required:"登录2日不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"threeDay":{
	    					required:"登录3日不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"fourDay":{
	    					required:"登录4日不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"fiveDay":{
	    					required:"登录5日不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"sixDay":{
	    					required:"登录6日不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"sevenDay":{
	    					required:"登录7日不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"regIntegration":{
	    					required:"注册送积分不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"rstate":{
	    					required:"请选择状态"
	    				},
	    				"regGrowth":{
	    					required:"注册送成长值不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"gstate":{
	    					required:"请选择状态"
	    				},
	    				"inviteIntegrati":{
	    					required:"邀请注册送积分不能为空",
	    					number:"请输入数值",
	    					min:"数值需大于0"
	    				},
	    				"istate":{
	    					required:"请选择状态"
	    				},
	    				"pcNotice":{
	    					required:"请输入pc端通告",
	    					maxlength:"pc端通告100个字以内"
	    				},
	    				"wapNotice":{
	    					required:"请输入WAP端通告",
	    					maxlength:"WAP端通告100个字以内"
	    				}
	    			}
	    		});
	    	   return formValidator ;
	    	}
    });
    
    module.exports = OrderListPager
});

