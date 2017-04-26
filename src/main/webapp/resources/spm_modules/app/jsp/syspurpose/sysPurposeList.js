define('app/jsp/syspurpose/sysPurposeList', function (require, exports, module) {
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
			//查询
            "click #search":"_searchSysPurposeList",
            "click #add":"_add",
            "click #update":"_update",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
        },
    	//重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchSysPurposeList();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	
    	_add:function(){
			window.location.href = _base+"/syspurpose/toAddPurpose";
		},
		_update:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
			var param = $("#dataForm").serializeArray();
			var url = _base + "/syspurpose/updateSysPurpose";
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
		_searchSysPurposeList:function(){
			var _this=this;
			var url = _base+"/syspurpose/getSysPurposePageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"sysPurposeListData",
				data : queryData,
				pageSize: OrderListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#sysPurposeListTemple");
						var htmlOut = template.render(data);
						$("#sysPurposeListData").html(htmlOut);
					}else{
						$("#sysPurposeListData").html("未搜索到信息");
					}
				},
			});
		},
		_getSearchParams:function(){
    		return {
    			"purposeCn":jQuery.trim($("#purposeCn").val()),
    			"site":jQuery.trim($("#site option:selected").val()),
    			"state":jQuery.trim($("#state option:selected").val()),
    			"createOperator":jQuery.trim($("#createOperator").val())
    		}
    	},
		_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    				purposeCn: {
    					maxlength:10
    					}
    			},
    			messages: {
    				purposeCn: {
    					maxlength:"最大长度不能超过{0}"
    					}
    			}
    		});
    		return formValidator;
    	},
    	_delete:function(purposeId){
			var _this=this;
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "删除数据中，请等待...",
				data: {purposeId:purposeId},
				url: _base + "/syspurpose/deleteSysPurpose",
				success: function (rs) {
					 window.location.reload();
				}
			});
		},
		_show:function(purposeId,language,site,purposeCn,remarks,sort,state){
			var _this= this;
    		$("#language").val("");
    		$("#purposeId").val("");
    		$("#updatePurposeCn").val("");
    		$("#updateRemarks").val("");
    		$("#updateSort").val("");
    		$(".site").val("");
    		$(".state").val("");
    		//赋值支付方式
    		//$("#payStyle").val(payStylePage);
			//弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			
			$("#language").val(language);
    		$("#updatePurposeCn").val(purposeCn);
    		$("#updateRemarks").val(remarks);
    		$("#updateSort").val(sort);
    		$("#purposeId").val(purposeId);
    		$(".site").val(site);
    		$(".state").val(state);
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
    		$("#errorMessage").html("");
    		$('#eject-mask').fadeOut(100);
    		$('#add-samll').slideUp(150);
    	}
    });
    
    module.exports = OrderListPager
});

