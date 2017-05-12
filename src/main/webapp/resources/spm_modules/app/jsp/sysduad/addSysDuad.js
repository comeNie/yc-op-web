define('app/jsp/sysduad/addSysDuad', function (require, exports, module) {
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
    		"click #save":"_save",
    		"click #add-close":"_closeDialog",
    		"blur #sourceCn":"_cheDuadCn",
    		"blur #targetCn":"_cheDuadCn"
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
			var url = _base + "/sysduad/insertSysDuad";
			ajaxController.ajax({
				type: "post",
				dataType:"json",
				processing: true,
				message: "保存数据中，请等待...",
				url: url,
				data: param,
				success: function (rs) {
					window.history.back(-1); 
				}
			});
		},
		_cheDuadCn:function(){
			var _this = this;
			var sourceCn = $("#sourceCn").val();
			var targetCn = $("#targetCn").val();
			//var language = $("#language").val();
			if(sourceCn != "" && sourceCn != null && targetCn != "" && targetCn != null){
				$.ajax({
					type: "post",
					data: {
						sourceCn,targetCn
					},
					url: _base + "/sysduad/checkDuadCn",
					success: function (data) {
						if(data >= 1){
							$(".check").html("此语言对已存在");
							$("#save").attr("disabled", true); 
						}else{
							$(".check").html("");
							$("#save").attr("disabled", false); 
						}
					}
				});
			}
		},
		_initValidate:function(){
	    	   var _this = this;
	    	   var formValidator = $("#dataForm").validate({
	    			rules: {
	    				"site":{
	    					required:true
	    				},
	    				"sourceCn":{
	    					required:true
	    				},
	    				"sourceEn":{
	    					required:true
	    				},
	    				"sourceCode":{
	    					required:true
	    				},
	    				"targetCn":{
	    					required:true
	    				},
	    				"targetEn":{
	    					required:true
	    				},
	    				"targetCode":{
	    					required:true
	    				},
	    				"ordinary":{
	    					required:true
	    				},
	    				"ordinaryUrgent":{
	    					required:true
	    				},
	    				"professional":{
	    					required:true
	    				},
	    				"professionalUrgent":{
	    					required:true
	    				},
	    				"publish":{
	    					required:true
	    				},
	    				"publishUrgent":{
	    					required:true
	    				},
	    				"ordinaryDollar":{
	    					required:true
	    				},
	    				"ourgentDollar":{
	    					required:true
	    				},
	    				"professionalDollar":{
	    					required:true
	    				},
	    				"purgentDollar":{
	    					required:true
	    				},
	    				"publishDollar":{
	    					required:true
	    				},
	    				"puburgentDollar":{
	    					required:true
	    				}
	    			},
	    			messages: {
	    				"site":{
	    					required:"请选择站点"
	    				},
	    				"sourceCn":{
	    					required:"请输入源语言中文名称"
	    				},
	    				"sourceEn":{
	    					required:"请输入源语言英文名称"
	    				},
	    				"sourceCode":{
	    					required:"请输入源语言编码"
	    				},
	    				"targetCn":{
	    					required:"请输入目标语言中文名称"
	    				},
	    				"targetEn":{
	    					required:"请输入目标语言英文名称"
	    				},
	    				"targetCode":{
	    					required:"请输入目标语言编码"
	    				},
	    				"ordinary":{
	    					required:"请输入普通级翻译价格"
	    				},
	    				"ordinaryUrgent":{
	    					required:"请输入加急普通级翻译价格"
	    				},
	    				"professional":{
	    					required:"请输入专业级翻译价格"
	    				},
	    				"professionalUrgent":{
	    					required:"请输入加急专业级翻译价格"
	    				},
	    				"publish":{
	    					required:"请输入出版级翻译价格"
	    				},
	    				"publishUrgent":{
	    					required:"请输入加急出版级翻译价格"
	    				},
	    				"ordinaryDollar":{
	    					required:"请输入普通级翻译美元价格"
	    				},
	    				"ourgentDollar":{
	    					required:"请输入加急普通级翻译美元价格"
	    				},
	    				"professionalDollar":{
	    					required:"请输入专业级翻译美元价格"
	    				},
	    				"purgentDollar":{
	    					required:"请输入加急专业级翻译美元价格"
	    				},
	    				"publishDollar":{
	    					required:"请输入出版级翻译美元价格"
	    				},
	    				"puburgentDollar":{
	    					required:"请输入加急出版级翻译美元价格"
	    				}
	    			}
	    		});
	    	   return formValidator ;
	    	},
	    	_closeDialog:function(){
	    		window.location.href = _base+"/sysduad/toSysDuadList";
	    	}
			
    });
    
    module.exports = OrderListPager
});

