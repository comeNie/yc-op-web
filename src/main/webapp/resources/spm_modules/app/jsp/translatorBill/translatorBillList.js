define('app/jsp/translatorBill/translatorBillList', function (require, exports, module) {
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
			//已处理
			"click #waitHandle":"_searchBillList",
			"click #refundId":"_searchBillList",
			"click #showQuery":"_showQueryInfo",
    		//查询
            "click #search":"_searchBillList",
            "click #update":"_updatePayState",
            "click #export":"_export",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
            
        },
    	//重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchBillList();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	
    	_rejectReviewOrder:function(orderId){
    		var param = {};
			var _this = this;
			var d = Dialog({
				content:'<textarea id="reasonDesc" style="width:200px;" class="int-text" maxlength="30"></textarea>',
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

    	_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
					account: {
    					required: true,
    					maxlength:30
    					},
					payStyle: {
						required: true
					}
    			},
    			messages: {
					account: {
    					required: "请输入账户!",
    					maxlength:"最大长度不能超过{0}"
    					},
					payStyle:{
						required: "请选择支付方式!",
					}
    			}
    		});
    		return formValidator;
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

		_searchBillList:function(){
			var _this=this;
			var url = _base+"/balance/getBillPageData";
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
			var stateBill="";
			var orderTimeS = $("#billTimeBegin").val();
			var orderTimeE = $("#billTimeEnd").val();
			var wait = $('#waitHandle').attr('class');
			var refund = $('#refundId').attr('class');
			if(wait="current" && wait!=""){
				stateBill = 2;
			}else if(refund="current" && refund!=""){
				stateBill=  1;//待审核
			}
    		return {
    			"beginDate":orderTimeS,
    			"endDate":orderTimeE,
    			"nickName":jQuery.trim($("#nickName").val()),
    			"acountType":jQuery.trim($("#accountType option:selected").val()),
    			"state":stateBill,
    		}
    	},
    	//弹出框
    	_popUp:function(billId,state,accountAmout){
    		var _this= this;
    		$("#updateMoney").val("");
    		$("#payStyle").val("");
    		$("#account").val("");
    		//赋值支付方式
    		//$("#payStyle").val(payStylePage);
			//弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			$("#updateMoney").val(accountAmout);
			$("#billIdUpdate").val(billId);
			$("#billState").val(state);
			// $("#payStyle").val(accountType);
    	},
    	_updatePayState:function(){
    		var _this= this;
    		var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
    		var billId = $("#billIdUpdate").val();
    		var money = $("#updateMoney").val();
    		var payStyle=jQuery.trim($("#payStyle option:selected").val());
			var settleAccount = $("#account").val();
			var billState = $("#billState").val();
    		$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/balance/settleBill",
				dataType : "json",
				data : {
					state:billState,
					billID:billId,
					accountType:payStyle,
					accountAmount:money,
					settleAccount:settleAccount
				},
				message : "正在加载数据..",
				success : function(data) {
					if(data.statusCode==1){
						//跳到列表页面
						window.location.href=_base+"/balance/toTranslatorBillList?random="+Math.random();
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
    	_detailPage:function(billId){
			window.location.href = _base+"/balance/toTranslatorDetailBillList?billID="+billId;
		}
    });
    
    module.exports = OrderListPager
});

