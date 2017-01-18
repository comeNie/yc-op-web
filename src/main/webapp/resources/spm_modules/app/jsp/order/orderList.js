define('app/jsp/order/orderList', function (require, exports, module) {
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
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		 "click #showQuery":"_showQueryInfo",
    		//查询
            "click #search":"_searchOrderList",
            "click #update":"_updatePayState",
            "click #export":"_export",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
            
        },
    	//重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchOrderList();
    		this._bindChlIdSelect();
    		this._bindOrdTypeSelect();
    		this._bindStateSelect();
    		this._bindPayStyleSelect();
    		this._bindLanguageSelect();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	
    	_rejectReviewOrder:function(orderId){
    		var param = {};
			var _this = this;
			var d = Dialog({
				content:'<textarea id="reasonDesc" style="width:200px;" class="int-text" maxlength="100"></textarea>',
				padding: 0,
				okValue: '驳回',
				title: '驳回原因:',
				ok:function(){
					param.reasonDesc = $("#reasonDesc").val();
					param.state = '42';
					param.orderIds = orderId;
					_this.handReviewOrder(param);
				},
				cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
		},
		_adoptReviewOrder:function(orderId){
			var _this = this;
			var param = {};
			var d = Dialog({
				content:"是否审核通过该订单？",
				okValue: '通过',
				title: '审核',
				ok:function(){
					param.state = '41';
					param.orderIds = orderId;
					_this.handReviewOrder(param);
				},
				cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
		},
		handReviewOrder:function(param){
			var _this=this;
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "保存数据中，请等待...",
				url: _base + "/order/handReviewOrder",
				data: param,
				success: function (data) {
					/*if(data.data=="00"){
						//如果通过调到待确认列表
						window.location.href=_base+"/toTbcOrderList";
					}else if(data.data=="11"){
						//如果通过调到翻译中列表
						window.location.href=_base+"/toTranslateOrderList";
					}else{*/
						_this._searchOrderList();
					//}
				}
			});
		},
    	_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
					remark: {
    					required: true,
    					maxlength:100
    					}
    			},
    			messages: {
    				remark: {
    					required: "请输入备注!",
    					maxlength:"最大长度不能超过{0}"
    					}
    			}
    		});
    		return formValidator;
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
		// 下拉订单状态
		_bindStateSelect : function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"STATE",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#searchOrderState").append('<option value="'+paramCode+'">'+paramName+'</option>');
					})
				}
			});
		},
		// 下拉 支付方式
		_bindPayStyleSelect : function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"PAY_STYLE",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#payStyle").append('<option value="'+paramCode+'">'+paramName+'</option>');
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
			var payTimeS=jQuery.trim($("#payTimeBegin").val());
			var payTimeE=jQuery.trim($("#payTimeEnd").val());
			if(orderTimeE=="" || orderTimeE==null){
				orderTimeE="";
			}else{
				orderTimeE= new Date( Date.parse( $("#orderTimeEnd").val().replace(/-/g,"/")) ).getTime();
			}
			if(orderTimeS=="" || orderTimeS==null){
				orderTimeS="";
			}else{
				orderTimeS= new Date( Date.parse( $("#orderTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			//支付时间
			if(payTimeS=="" || payTimeS==null){
				payTimeS="";
			}else{
				payTimeS= new Date( Date.parse( $("#payTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			if(payTimeE=="" || payTimeE==null){
				payTimeE="";
			}else{
				payTimeE= new Date( Date.parse( $("#payTimeEnd").val().replace(/-/g,"/")) ).getTime();
			}
			var userName=jQuery.trim($("#nickName").val());
			var chlId=jQuery.trim($("#orderSource option:selected").val());
			var translateType=jQuery.trim($("#orderType option:selected").val());
			var langungePaire=jQuery.trim($("#langugePaire option:selected").val());
			var state=jQuery.trim($("#searchOrderState").val());
			var orderPageId=jQuery.trim($("#orderId").val());
			var payStyle = jQuery.trim($("#payStyle option:selected").val());
			window.location.href=_base+'/order/export?orderTimeS='+orderTimeS+'&orderTimeE='+orderTimeE+'&payTimeS='+payTimeS+
			'&userName='+userName+'&chlId='+chlId+'&translateType='+translateType+'&langungePaire='+langungePaire+'&state='
		+state+'&orderPageId='+orderPageId+'&payTimeE='+payTimeE+'&payStyle='+payStyle+"&offset="+today.stdTimezoneOffset();
		},
		_searchOrderList:function(){
			var _this=this;
			var url = _base+"/order/getOrderPageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"orderListData",
				data : queryData,
				pageSize: OrderListPager.DEFAULT_PAGE_SIZE,
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
			var payTimeS = $("#payTimeBegin").val();
			var payTimeE = $("#payTimeEnd").val();
			if(orderTimeE=="" || orderTimeE==null){
				orderTimeE="";
			}else{
				orderTimeE= new Date( Date.parse( $("#orderTimeEnd").val().replace(/-/g,"/"))).getTime();
			}
			if(orderTimeS=="" || orderTimeS==null){
				orderTimeS="";
			}else{
				orderTimeS= new Date( Date.parse( $("#orderTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			//支付时间
			if(payTimeS=="" || payTimeS==null){
				payTimeS="";
			}else{
				payTimeS= new Date( Date.parse( $("#payTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			if(payTimeE=="" || payTimeE==null){
				payTimeE="";
			}else{
				payTimeE= new Date( Date.parse( $("#payTimeEnd").val().replace(/-/g,"/")) ).getTime();
			}
    		return {
    			"orderTimeS":orderTimeS,
    			"orderTimeE":orderTimeE,
    			"payTimeS":payTimeS,
    			"payTimeE":payTimeE,
    			"userName":jQuery.trim($("#nickName").val()),
    			"chlId":jQuery.trim($("#orderSource option:selected").val()),
    			"translateType":jQuery.trim($("#orderType option:selected").val()),
    			"langungePaire":jQuery.trim($("#langugePaire option:selected").val()),
    			"state":jQuery.trim($("#searchOrderState").val()),
    			"orderPageId":jQuery.trim($("#orderId").val()),
    			"payStyle":jQuery.trim($("#payStyle option:selected").val())
    		}
    	},
    	//弹出框
    	_popUp:function(orderId,payStylePage,currencyUnit,payStyle,updateMoney,realMoney){
    		var _this= this;
    		$("#orderIdUpdate").val("");
    		$("#payStyleUpdate").val("");
    		$("#currencyUnitUpdate").val("");
    		$("#updateMoney").val("");
    		$("#realmoney").val("");
    		//赋值支付方式
    		//$("#payStyle").val(payStylePage);
			//弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			$("#orderIdUpdate").val(orderId);
			$("#payStyleUpdate").val(payStyle);
			$("#currencyUnitUpdate").val(currencyUnit);
			$("#updateMoney").val(updateMoney);
			$("#realmoney").val(realMoney);
    	},
    	_updatePayState:function(){
    		var _this= this;
    		var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
    		var orderId = $("#orderIdUpdate").val();
    		var money = $("#realmoney").val();
    		var remak = $("#remark").val();
    		var payStyle="HK";
    		var currencyUnit=$("#currencyUnitUpdate").val();
    		$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/updatePayState",
				dataType : "json",
				data : {
					orderId:orderId,
					remark:remak,
					updateFee:money,
					currencyUnit:currencyUnit,
					payStyle:payStyle
				},
				message : "正在加载数据..",
				success : function(data) {
					if(data.statusCode==1){
						//跳到列表页面
						window.location.href=_base+"/order/toOrderList";
					}else{
						var d = Dialog({
							title: '消息',
							content:"修改失败",
							icon:'prompt',
							okValue: '确 定',
							ok:function(){
								this.close();
							}
						});
						d.show();
					}
				}
			});
    	},
    	_closeDialog:function(){
    		$("#errorMessage").html("");
    		$('#eject-mask').fadeOut(100);
    		$('#add-samll').slideUp(150);
    	},
    	_detailPage:function(orderId){
			window.location.href = _base+"/order/orderdetails?orderId="
            + orderId+'&mod=edit'+'&isAll=all';
		}
    });
    
    module.exports = OrderListPager
});

