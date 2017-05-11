define('app/jsp/syspurpose/addSysPurpose', function (require, exports, module) {
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
    		"blur #purposeCn":"_chePurposeCn"
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
			var url = _base + "/syspurpose/insertSysPurpose";
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
		
		_chePurposeCn:function(){
			var _this = this;
			var purposeCn = $("#purposeCn").val();
			/*var language = $("#language").val();*/
			if(purposeCn != "" && purposeCn != null){
				$.ajax({
					type: "post",
					data: {
						purposeCn
					},
					url: _base + "/syspurpose/checkPurposeCn",
					success: function (data) {
						if(data >= 1){
							$(".check").html("此名称已存在");
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
	    				"purposeCn":{
	    					required:true,
	    					maxlength:10
	    				},
	    				"remarks":{
	    					maxlength:50
	    				},
	    				"sort":{
	    					digits:true
	    				}
	    			},
	    			messages: {
	    				"site":{
	    					required:"请选择站点"
	    				},
	    				"purposeCn":{
	    					required:"请输入用途名称",
	    					maxlength:"用途名称10个字以内"
	    				},
	    				"remarks":{
	    					maxlength:"描述50个字以内"
	    				},
	    				"sort":{
	    					number:"输入的排序格式不正确"
	    				}
	    			}
	    		});
	    	   return formValidator ;
	    	},
	    	_closeDialog:function(){
	    		window.location.href = _base+"/syspurpose/toSysPurposeList";
	    	}
			
    });
    
    module.exports = OrderListPager
});

