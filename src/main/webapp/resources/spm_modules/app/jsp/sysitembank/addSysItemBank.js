define('app/jsp/sysitembank/addSysItemBank', function (require, exports, module) {
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
    		"blur #langugePaire":"_cheDuadCn",
    		"click #add-close":"_closeDialog"
        },
    	//重写父类
    	setup: function () {
    		var _this = this;
    		this._bindLanguageSelect();
    		OrderListPager.superclass.setup.call(this);
    	},
    	// 下拉 语种方向
		_bindLanguageSelect:function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getLangugeSelect",
				dataType : "json",
				data : {
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var langugeName = d[index].sourceCn+"->"+d[index].targetCn;
						var langugeCode = d[index].duadId;
						$("#langugePaire").append('<option value="'+langugeCode+'">'+langugeName+'</option>');
					})
				}
			});
		},
		_cheDuadCn:function(){
			var _this = this;
			var duadId = $("#langugePaire").val();
			if(langugePaire != "" && langugePaire != null){
				$.ajax({
					type: "post",
					data: {
						duadId
					},
					url: _base + "/sysitembank/checkDuadCn",
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
		_save:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
			var param = $("#dataForm").serializeArray();
			var url = _base + "/sysitembank/insertSysItemBank";
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
	    				"site":{
	    					required:true
	    				},
	    				"qname":{
	    					required:true,
	    					maxlength:10
	    				}
	    			},
	    			messages: {
	    				"site":{
	    					required:"请选择站点"
	    				},
	    				"qname":{
	    					required:"请输入题库名称",
	    					maxlength:"题库名称10个字以内"
	    				}
	    			}
	    		});
	    	   return formValidator ;
	    	},
	    	_closeDialog:function(){
	    		window.location.href = _base+"/sysitembank/toSysItemBankList";
	    	}
			
    });
    
    module.exports = OrderListPager
});

