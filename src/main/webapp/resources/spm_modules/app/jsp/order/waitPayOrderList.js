define('app/jsp/order/waitPayOrderList', function (require, exports, module) {
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
    require("twbs-pagination/jquery.twbsPagination.min");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var WaitPayOrderListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		 "click #showQuery":"_showQueryInfo",
    		//查询
            "click #search":"_searchOrderList",
            "click #export":"_export",
            "click #update":"_updatePayState",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
            
        },
    	//重写父类
    	setup: function () {
    		WaitPayOrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchOrderList();
    		this._bindChlIdSelect();
    		this._bindOrdTypeSelect();
    		this._bindLanguageSelect();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    				updateMoney: {
    					required: true,
    					moneyNumber: true
    					},
					remark: {
    					required: true,
    					maxlength:100
    				}
    			},
    			messages: {
    				updateMoney: {
    					required:"请输入修改金额!",
    					moneyNumber:"格式不对！"
    				},
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
		
		_searchOrderList:function(){
			var _this=this;
			var url = _base+"/getWaitPayOrderData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"orderListData",
				data : queryData,
				pageSize: WaitPayOrderListPager.DEFAULT_PAGE_SIZE,
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
		_export:function(){
			var _this=this;
			var orderTimeS=jQuery.trim($("#orderTimeBegin").val());
			var orderTimeE=jQuery.trim($("#orderTimeEnd").val());
			var userName=jQuery.trim($("#nickName").val());
			var chlId=jQuery.trim($("#orderSource option:selected").val());
			var translateType=jQuery.trim($("#orderType option:selected").val());
			var langungePaire=jQuery.trim($("#langugePaire option:selected").val());
			var orderPageId=jQuery.trim($("#orderId").val());
			var updateTimeS=jQuery.trim($("#updateTimeBegin").val());
			var updateTimeE=jQuery.trim($("#updateTimeEnd").val());
			if(orderTimeS=="" || orderTimeS==null){
				orderTimeS="";
			}else{
				orderTimeS= new Date( Date.parse( $("#orderTimeBegin").val().replace(/-/g,"/")+" 00:00:00" ) ).getTime();
			}
			if(orderTimeE=="" || orderTimeE==null){
				orderTimeE="";
			}else{
				orderTimeE= new Date( Date.parse( $("#orderTimeEnd").val().replace(/-/g,"/")+" 23:59:59" ) ).getTime();
			}
			//修改时间
			if(updateTimeS=="" || updateTimeS==null){
				updateTimeS="";
			}else{
				updateTimeS= new Date( Date.parse( $("#updateTimeBegin").val().replace(/-/g,"/")+" 00:00:00" ) ).getTime();
			}
			if(updateTimeE=="" || updateTimeE==null){
				updateTimeE="";
			}else{
				updateTimeE= new Date( Date.parse( $("#updateTimeEnd").val().replace(/-/g,"/")+" 23:59:59" ) ).getTime();
			}
			var updateOperName=jQuery.trim($("#updateName").val());
			window.location.href=_base+'/exportWaitPayOrd?orderTimeS='+orderTimeS+'&orderTimeE='+orderTimeE+
			'&userName='+userName+'&chlId='+chlId+'&translateType='+translateType+'&langungePaire='+langungePaire
		+'&orderPageId='+orderPageId+'&updateTimeS='+updateTimeS+'&updateTimeE='+updateTimeE+'&updateOperName='+updateOperName;
		},
		_getSearchParams:function(){
			var orderTimeS = $("#orderTimeBegin").val();
			var orderTimeE = $("#orderTimeEnd").val();
			var updateTimeS =  $("#updateTimeBegin").val();
			var updateTimeE =  $("#updateTimeEnd").val();
			if(orderTimeS=="" || orderTimeS==null){
				orderTimeS=null;
			}else{
				orderTimeS= new Date( Date.parse( $("#orderTimeBegin").val().replace(/-/g,"/")+" 00:00:00" ) ).getTime();
			}
			if(orderTimeE=="" || orderTimeE==null){
				orderTimeE=null;
			}else{
				orderTimeE= new Date( Date.parse( $("#orderTimeEnd").val().replace(/-/g,"/")+" 23:59:59" ) ).getTime();
			}
			//修改时间
			if(updateTimeS=="" || updateTimeS==null){
				updateTimeS=null;
			}else{
				updateTimeS= new Date( Date.parse( $("#updateTimeBegin").val().replace(/-/g,"/")+" 00:00:00" ) ).getTime();
			}
			if(updateTimeE=="" || updateTimeE==null){
				updateTimeE=null;
			}else{
				updateTimeE= new Date( Date.parse( $("#updateTimeEnd").val().replace(/-/g,"/")+" 23:59:59" ) ).getTime();
			}
    		return {
    			"orderTimeS":orderTimeS,
    			"orderTimeE":orderTimeE,
    			"updateTimeS":updateTimeS,
    			"updateTimeE":updateTimeE,
    			"userName":jQuery.trim($("#nickName").val()),
    			"chlId":jQuery.trim($("#orderSource option:selected").val()),
    			"translateType":jQuery.trim($("#orderType option:selected").val()),
    			"langungePaire":jQuery.trim($("#langugePaire option:selected").val()),
    			"orderPageId":jQuery.trim($("#orderId").val()),
    			"updateOperName":jQuery.trim($("#updateName").val())
    		}
    	},
    	//弹出框
    	_popUp:function(orderId,payStylePage,currencyUnit,payStyle){
    		var _this= this;
    		$("#orderIdUpdate").val("");
    		$("#payStyleUpdate").val("");
    		$("#currencyUnitUpdate").val("");
    		//赋值支付方式
    		//$("#payStyle").val(payStylePage);
			//弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			$("#orderIdUpdate").val(orderId);
			$("#payStyleUpdate").val(payStyle);
			$("#currencyUnitUpdate").val(currencyUnit);
    	},
    	_updatePayState:function(){
    		var _this= this;
    		var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
    		var orderId = $("#orderIdUpdate").val();
    		var money = $("#updateMoney").val();
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
						window.location.href=_base+"/toWaitPayOrderList";
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
            + orderId+'&mod=edit';
		}
    });
    
    module.exports = WaitPayOrderListPager
});

