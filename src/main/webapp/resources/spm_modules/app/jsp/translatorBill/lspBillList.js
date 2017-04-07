define('app/jsp/translatorBill/lspBillList', function (require, exports, module) {
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
    var LspListPager = Widget.extend({
    	
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
			"click #domestic":"_searchBillList",
			"click #foreign":"_searchBillList",
			//查询
            "click #search":"_searchBillList",
            "click #update":"_updatePayState",
            "click #export":"_export",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
            
        },
    	//重写父类
    	setup: function () {
			LspListPager.superclass.setup.call(this);
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
			var stateBill="";
			var flag="";
			var orderTimeS = $("#billTimeBegin").val();
			var orderTimeE = $("#billTimeEnd").val();
			var wait = $('#waitHandle').attr('class');
			var refund = $('#refundId').attr('class');
			var domestic = $('#domestic').attr('class');
			var foreign = $('#foreign').attr('class');
			var nickName = jQuery.trim($("#nickName").val());
			var acountType = jQuery.trim($("#accountType option:selected").val());
			if(domestic=="current1" && domestic!=""){
				flag = 0;
			}else if(foreign=="current1" && foreign!=""){
				flag=  1;//待审核
			}
			if(wait=="current" && wait!=""){
				stateBill = 1;
			}else if(refund=="current" && refund!=""){
				stateBill=  2;//待审核
			}
			window.location.href=_base+'/balance/export?flag='+flag+'&beginDate='+orderTimeS+'&endDate='+orderTimeE+
			'&nickName='+nickName+'&acountType='+acountType+'&state='+stateBill+"&offset="+today.stdTimezoneOffset();
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
				pageSize: LspListPager.DEFAULT_PAGE_SIZE,
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
			var flag="";
			var orderTimeS = $("#billTimeBegin").val();
			var orderTimeE = $("#billTimeEnd").val();
			var wait = $('#waitHandle').attr('class');
			var refund = $('#refundId').attr('class');
			var domestic = $('#domestic').attr('class');
			var foreign = $('#foreign').attr('class');
			var lspName = jQuery.trim($('#lspName').val());
			var lspAdmin = jQuery.trim($('#lspAdmin').val());
			if(domestic="current1" && domestic!=""){
				flag = 0;
			}else if(foreign="current1" && foreign!=""){
				flag=  1;//待审核
			}
			if(wait="current" && wait!=""){
				stateBill = 1;
			}else if(refund="current" && refund!=""){
				stateBill=  2;//待审核
			}
    		return {
				"flag":flag,
    			"beginDate":orderTimeS,
    			"endDate":orderTimeE,
    			"nickName":jQuery.trim($("#nickName").val()),
    			"acountType":jQuery.trim($("#accountType option:selected").val()),
    			"state":stateBill,
				"targetType":"4",
				"lspName":lspName,
				"lspAdmin":lspAdmin,
    		}
    	},
    	//弹出框
    	_popUp:function(billId,state,flag,accountAmout){
    		var _this= this;
    		$("#updateMoney").val("");
    		$("#payStyle").val("");
    		$("#account").val("");
    		//赋值支付方式
    		//$("#payStyle").val(payStylePage);
			//弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			var currenUnit;
			if(flag=="1"){
				currenUnit = "$"
			}else {
				currenUnit = "¥"
			}
			$("#updateMoney").val(currenUnit+accountAmout);
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
			var money = $("#updateMoney").val().substring(1,$("#updateMoney").val().length)*1000;
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
						window.location.href=_base+"/lspBill/toLspBillList?random="+Math.random();
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
			window.location.href = _base+"/lspBill/toLspDetailBillList?billID="+billId;
		}
    });
    
    module.exports = LspListPager
});

