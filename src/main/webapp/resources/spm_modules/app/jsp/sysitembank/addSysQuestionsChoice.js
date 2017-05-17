define('app/jsp/sysitembank/addSysQuestionsChoice', function (require, exports, module) {
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
    		"click #add-close":"_closeDialog"
        },
    	//重写父类
    	setup: function () {
    		var _this = this;
    		this._bindLanguageSelect();
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
			var url = _base + "/sysquestions/insertSysQuestions";
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
		
		_initValidate:function(){
	    	   var _this = this;
	    	   var formValidator = $("#dataForm").validate({
	    			rules: {
	    				"choiceQuestion":{
	    					required:true
	    				},
	    				"optiona":{
	    					required:true
	    				},
	    				"optionb":{
	    					required:true
	    				},
	    				"optionc":{
	    					required:true
	    				},
	    				"optiond":{
	    					required:true
	    				},
	    				"state":{
	    					required:true
	    				}
	    			},
	    			messages: {
	    				"choiceQuestion":{
	    					required:"请输入标题"
	    				},
	    				"optiona":{
	    					required:"请输入A选项"
	    				},
	    				"optionb":{
	    					required:"请输入B选项"
	    				},
	    				"optionc":{
	    					required:"请输入C选项"
	    				},
	    				"optiond":{
	    					required:"请输入D选项"
	    				},
	    				"state":{
	    					required:"请选择状态"
	    				}
	    			}
	    		});
	    	   return formValidator ;
	    	},
	    	_closeDialog:function(){
	    		window.history.back(-1); 
	    	}
			
    });
    
    module.exports = OrderListPager
});

