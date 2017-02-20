define('app/jsp/order/evaluateOrderList', function (require, exports, module) {
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
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var EvaluteOrderListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		 "click #showQuery":"_showQueryInfo",
    		//查询
            "click #search":"_searchOrderList",
            "click #export":"_export"
            
        },
    	//重写父类
    	setup: function () {
    		EvaluteOrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchOrderList();
    		this._bindChlIdSelect();
    		this._bindOrdTypeSelect();
    		this._bindLanguageSelect();
    	},
    	_showQueryInfo: function(){
			//展示查询条件
			var info= $("#selectDiv").is(":hidden"); //是否隐藏
		    if(info==true){
		    	$("#selectDiv").show();
		    }else{
		    	$("#selectDiv").hide();
		    }
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
						var langugeName = d[index].transTypeName+"->"+d[index].sourceCn+"->"+d[index].targetCn;
						var langugeCode = d[index].duadId;
						$("#langugePaire").append('<option value="'+langugeCode+'">'+langugeName+'</option>');
					})
				}
			});
		},
		// 下拉订单类型（对应库中的翻译类型）
		_bindOrdTypeSelect:function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"TRANSLATE_TYPE",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#orderType").append('<option value="'+paramCode+'">'+paramName+'</option>');
					})
				}
			});
		},
		// 下拉 订单来源
		_bindChlIdSelect : function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"CHL_ID",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#orderSource").append('<option value="'+paramCode+'">'+paramName+'</option>');
					})
				}
			});
		},
		_export:function(){
			var _this=this;
			var evaluteTimeS=jQuery.trim($("#evaluatTimeBegin").val());
			var evaluteTimeE=jQuery.trim($("#evaluatEnd").val());
			if(evaluteTimeE=="" || evaluteTimeE==null){
				evaluteTimeE="";
			}else{
				evaluteTimeE= new Date( Date.parse( $("#evaluatEnd").val().replace(/-/g,"/")) ).getTime();
			}
			if(evaluteTimeS=="" || evaluteTimeS==null){
				evaluteTimeS="";
			}else{
				evaluteTimeS= new Date( Date.parse( $("#evaluatTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			var userName=jQuery.trim($("#nickName").val());
			var lspName = jQuery.trim($("#lspName").val());
			var interperName = jQuery.trim($("#interperName").val());
			var chlId=jQuery.trim($("#orderSource option:selected").val());
			var translateType=jQuery.trim($("#orderType option:selected").val());
			var langungePaire=jQuery.trim($("#langugePaire option:selected").val());
			var orderPageId=jQuery.trim($("#orderId").val());
			window.location.href=_base+'/evaluateExport?evaluateTimeE='+evaluteTimeE+'&evaluateTimeS='+evaluteTimeS+
			'&userName='+userName+'&chlId='+chlId+'&translateType='+translateType+'&langungePaire='+langungePaire
		    +'&orderPageId='+orderPageId+'&lspName='+lspName+'&interperName='+interperName+
		    "&offset="+today.stdTimezoneOffset();
		},
		_searchOrderList:function(){
			var _this=this;
			var url = _base+"/getEvluateOrdPageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"orderListData",
				data : queryData,
				pageSize: EvaluteOrderListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#orderListTemple");
						var htmlOut = template.render(data);
						$("#orderListData").html(htmlOut);
					}else{
						$("#orderListData").html("未搜索到信息");
					}
				},
			});
		},
	
		_getSearchParams:function(){
			var ealuateTimeS = $("#evaluatTimeBegin").val();
			var ealuateTimeE = $("#evaluatEnd").val();
			if(ealuateTimeS=="" || ealuateTimeS==null){
				ealuateTimeS=null;
			}else{
				ealuateTimeS= new Date( Date.parse( $("#evaluatTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			if(ealuateTimeE=="" || ealuateTimeE==null){
				ealuateTimeE=null;
			}else{
				ealuateTimeE= new Date( Date.parse( $("#evaluatEnd").val().replace(/-/g,"/")) ).getTime();
			}
    		return {
    			"evaluateTimeS":ealuateTimeS,
    			"evaluateTimeE":ealuateTimeE,
    			"userName":jQuery.trim($("#nickName").val()),
    			"lspName":jQuery.trim($("#lspName").val()),
    			"interperName":jQuery.trim($("#interperName").val()),
    			"chlId":jQuery.trim($("#orderSource option:selected").val()),
    			"translateType":jQuery.trim($("#orderType option:selected").val()),
    			"langungePaire":jQuery.trim($("#langugePaire option:selected").val()),
    			"orderPageId":jQuery.trim($("#orderId").val())
    		}
    	},
    	_detailPage:function(orderId){
    			window.location.href = _base+"/toEvaluteOrdDetail?orderId="
	            + orderId+"&mod=edit&random="+Math.random();
		}
		
    });
    
    module.exports = EvaluteOrderListPager
});

