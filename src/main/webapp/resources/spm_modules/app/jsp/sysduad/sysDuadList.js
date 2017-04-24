define('app/jsp/sysduad/sysDuadList', function (require, exports, module) {
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
    // 实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    // 定义页面组件类
    var OrderListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	// 属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 20
    	},
    	// 事件代理
    	events: {
			// 查询
            "click #search":"_searchSysDuadList",
            "click #add":"_add",
            "click #update":"_update",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
        },
    	// 重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchSysDuadList();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	
    	_add:function(){
			window.location.href = _base+"/sysduad/toAddDuad";
		},

		_searchSysDuadList:function(){
			var _this=this;
			var url = _base+"/sysduad/getSysDuadPageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"sysDuadListData",
				data : queryData,
				pageSize: OrderListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#sysDuadListTemple");
						var htmlOut = template.render(data);
						$("#sysDuadListData").html(htmlOut);
					}else{
						$("#sysDuadListData").html("未搜索到信息");
					}
				},
			});
		},
		_getSearchParams:function(){
    		return {
    			"language":jQuery.trim($("#language option:selected").val()),
    			"site":jQuery.trim($("#site option:selected").val()),
    			"state":jQuery.trim($("#state option:selected").val())
    		}
    	},
		_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    			},
    			messages: {
    			}
    		});
    		return formValidator;
    	},
    	_show:function(duadId,language,site,sourceCn,targetCn,ordinary,ordinaryUrgent,professional,professionalUrgent,publish,publishUrgent,sort,state){
			var _this= this;
    		$("#language").val("");
    		$("#duadId").val("");
    		$("#sourceCn").val("");
    		$("#targetCn").val("");
    		$("#ordinary").val("");
    		$("#ordinaryUrgent").val("");
    		$("#professional").val("");
    		$("#professionalUrgent").val("");
    		$("#publish").val("");
    		$("#publishUrgent").val("");
    		$("#sort").val("");
    		$(".site").val("");
    		$(".state").val("");
			// 弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			$("#language").val(language);
    		$("#duadId").val(duadId);
    		$("#sourceCn").val(sourceCn);
    		$("#targetCn").val(targetCn);
    		$("#ordinary").val(ordinary);
    		$("#ordinaryUrgent").val(ordinaryUrgent);
    		$("#professional").val(professional);
    		$("#professionalUrgent").val(professionalUrgent);
    		$("#publish").val(publish);
    		$("#publishUrgent").val(publishUrgent);
    		$("#sort").val(sort);
    		$(".site").val(site);
    		$(".state").val(state);
		},
		_update:function(){
			var _this = this;
			var param = $("#dataForm").serializeArray();
			var url = _base + "/sysduad/updateSysDuad";
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
		_closeDialog:function(){
    		$("#errorMessage").html("");
    		$('#eject-mask').fadeOut(100);
    		$('#add-samll').slideUp(150);
    	}
    });
    
    module.exports = OrderListPager
});

