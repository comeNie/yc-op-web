define('app/jsp/order/updatingOrderList', function (require, exports, module) {
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
    var UpdatingOrdListPager = Widget.extend({
    	
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
    		UpdatingOrdListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchOrderList();
    		this._bindChlIdSelect();
    		this._bindOrdTypeSelect();
    		this._bindOrdLeveleSelect();
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
		// 下拉 订单级别
		_bindOrdLeveleSelect : function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"ORDER_LEVEL",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#orderLevel").append('<option value="'+paramCode+'">'+paramName+'</option>');
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
			var orderTimeS=jQuery.trim($("#orderTimeBegin").val());
			var orderTimeE=jQuery.trim($("#orderTimeEnd").val());
			var lockTimeS=jQuery.trim($("#lockTimeBegin").val());
			var lockTimeE=jQuery.trim($("#lockTimeEnd").val());
			if(orderTimeS=="" || orderTimeS==null){
				orderTimeS="";
			}else{
				orderTimeS= new Date( Date.parse( $("#orderTimeBegin").val().replace(/-/g,"/") ) ).getTime();
			}
			if(orderTimeE=="" || orderTimeE==null){
				orderTimeE="";
			}else{
				orderTimeE= new Date( Date.parse( $("#orderTimeEnd").val().replace(/-/g,"/")) ).getTime();
			}
			//领取时间
			if(lockTimeS=="" || lockTimeS==null){
				lockTimeS="";
			}else{
				lockTimeS= new Date( Date.parse( $("#lockTimeBegin").val().replace(/-/g,"/") ) ).getTime();
			}
			if(lockTimeE=="" || lockTimeE==null){
				lockTimeE="";
			}else{
				lockTimeE= new Date( Date.parse( $("#lockTimeEnd").val().replace(/-/g,"/")) ).getTime();
			}
			var userName=jQuery.trim($("#nickName").val());
			var chlId=jQuery.trim($("#orderSource option:selected").val());
			var translateType=jQuery.trim($("#orderType option:selected").val());
			var langungePaire=jQuery.trim($("#langugePaire option:selected").val());
			var orderPageId=jQuery.trim($("#orderId").val());
			var orderLevel = jQuery.trim($("#orderLevel option:selected").val());
			window.location.href=_base+'/updatingExport?orderTimeS='+orderTimeS+'&orderTimeE='+orderTimeE+'&lockTimeS='+lockTimeS+
			'&userName='+userName+'&chlId='+chlId+'&translateType='+translateType+'&langungePaire='+langungePaire+
		    '&orderPageId='+orderPageId+'&lockTimeE='+lockTimeE+'&orderLevel='+orderLevel+"&offset="+today.stdTimezoneOffset();
		},
		_searchOrderList:function(){
			var _this=this;
			var url = _base+"/getUpdatingOrdData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"orderListData",
				data : queryData,
				pageSize: UpdatingOrdListPager.DEFAULT_PAGE_SIZE,
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
			var orderTimeS = $("#orderTimeBegin").val();
			var orderTimeE = $("#orderTimeEnd").val();
			var lockTimeS =  $("#lockTimeBegin").val();
			var lockTimeE =  $("#lockTimeEnd").val();
			if(orderTimeS=="" || orderTimeS==null){
				orderTimeS="";
			}else{
				orderTimeS= new Date( Date.parse( $("#orderTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			if(orderTimeE=="" || orderTimeE==null){
				orderTimeE="";
			}else{
				orderTimeE= new Date( Date.parse( $("#orderTimeEnd").val().replace(/-/g,"/") ) ).getTime();
			}
			//领取时间
			if(lockTimeS=="" || lockTimeS==null){
				lockTimeS="";
			}else{
				lockTimeS= new Date( Date.parse( $("#lockTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			if(lockTimeE=="" || lockTimeE==null){
				lockTimeE="";
			}else{
				lockTimeE= new Date( Date.parse( $("#lockTimeEnd").val().replace(/-/g,"/")) ).getTime();
			}
    		return {
    			"orderTimeS":orderTimeS,
    			"orderTimeE":orderTimeE,
    			"lockTimeS":lockTimeS,
    			"lockTimeE":lockTimeE,
    			"userName":jQuery.trim($("#nickName").val()),
    			"chlId":jQuery.trim($("#orderSource option:selected").val()),
    			"translateType":jQuery.trim($("#orderType option:selected").val()),
    			"langungePaire":jQuery.trim($("#langugePaire option:selected").val()),
    			"orderPageId":jQuery.trim($("#orderId").val()),
    			"orderLevel":jQuery.trim($("#orderLevel option:selected").val())
    		}
    	},
    	_detailPage:function(orderId){
			window.location.href = _base+"/order/orderdetails?orderId="
            + orderId+'&mod=edit'+"&random="+Math.random();
    	},
    	_rejectRefundOrder:function(orderId){
			var _this = this;
			var param={};
			param.orderId=orderId;
			var d = Dialog({
				content:'<textarea id="reasonDesc" style="width:200px;" class="int-text"  maxlength="100"></textarea>',
				padding: 0,
				okValue: '确认',
				title: '退款原因:',
				ok:function(){
					param.busiType='2';
					param.reasonDesc = $("#reasonDesc").val();
					param.state = '40';
					param.displayFlag='40';
					_this._handReviewOrder(param);
				},
				cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
		},
    	_handRefundOrder:function(param){
			var _this=this;
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "保存数据中，请等待...",
				url: _base + "/refundApply",
				data: param,
				success: function (data) {
					window.location.href=_base+"/order/toReviewOrderList";
				}
			});
		}
		
    });
    
    module.exports = UpdatingOrdListPager
});

