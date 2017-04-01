define('app/jsp/translatorBill/businessList', function (require, exports, module) {
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
    var BusinessListPager = Widget.extend({
    	
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
			"click #income":"_searchBillList",
			"click #out":"_searchBillList",
			"click #detail":"_searchBillList",
			//查询
            "click #search":"_searchBillList",
            "click #export":"_export",
        },
    	//重写父类
    	setup: function () {
			BusinessListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchBillList();
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
			if(domestic="current1" && domestic!=""){
				flag = 0;
			}else if(foreign="current1" && foreign!=""){
				flag=  1;//待审核
			}
			if(wait="current" && wait!=""){
				stateBill = 2;
			}else if(refund="current" && refund!=""){
				stateBill=  1;//待审核
			}
			window.location.href=_base+'/balance/export?flag='+flag+'&beginDate='+orderTimeS+'&endDate='+orderTimeE+
			'&nickName='+nickName+'&acountType='+acountType+'&state='+stateBill+"&offset="+today.stdTimezoneOffset();
		},

		_searchBillList:function(){
			var _this=this;
			var url = _base+"/businessList/getIncomeOutPageData";
			var queryData = this._getSearchParams();
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/businessList/getIncomeOutData",
				dataType : "json",
				data : queryData,
				message : "正在加载数据..",
				success : function(data) {
					if(data) {
						if (queryData.incomeFlag == "" && queryData.optType == "") {
							document.getElementById("show").innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:#333333;'>收入 " + data.data.incomeNum + " 笔,共 </span><span style='color:#33CCFF;'>" +
								data.data.incomeAmountRMB / 1000 + "</span><span style='color:#333333;'> 元,</span><span style='color:#0099CC;'>" +
								data.data.incomeAmountUSD / 1000 + "</span><span style='color:#333333;'>美元;&nbsp;&nbsp;支出 " + data.data.outNum + " 笔,共 </span>" +
								"<span style='color:#FF6633;'>" + (0 - data.data.outAmountRMB / 1000) + "</span><span style='color:#333333;'> 元,</span>" +
								"<span style='color:#FF9900;'>" + (0 - data.data.outAmountUSD / 1000) + "</span><span style='color:#333333;'>美元</span>";
						}
						if (queryData.incomeFlag == "1" && queryData.optType == "1") {
							document.getElementById("show").innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:#333333;'>收入 " + data.data.incomeNum + " 笔,共 </span><span style='color:#33CCFF;'>" +
								data.data.incomeAmountRMB / 1000 + "</span><span style='color:#333333;'> 元,</span><span style='color:#0099CC;'>" +
								data.data.incomeAmountUSD / 1000 + "</span><span style='color:#333333;'>美元;&nbsp;&nbsp;";
						}
						if (queryData.incomeFlag == "0") {
							document.getElementById("show").innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;支出 " + data.data.outNum + " 笔,共 </span>" +
								"<span style='color:#FF6633;'>" + (0 - data.data.outAmountRMB / 1000) + "</span><span style='color:#333333;'> 元,</span>" +
								"<span style='color:#FF9900;'>" + (0 - data.data.outAmountUSD / 1000) + "</span><span style='color:#333333;'>美元</span>";
						}
					}

				}
			}),
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"businessListData",
				data : queryData,
				pageSize: BusinessListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#businessListTemple");
						var htmlOut = template.render(data);
						$("#businessListData").html(htmlOut);
					}else{
						$("#businessListData").html("未搜索到信息");
					}
				},
			});
		},
		
		_getSearchParams:function(){
			var serialCode=jQuery.trim($("#serialCode").val());
			var nickName=jQuery.trim($("#nickName").val());
			var beginDate = $('#timeBegin').val();
			var endDate = $("#timeEnd").val();
			
			//支付方式 智联在线;支付宝;微信;线下充值
			var channel = jQuery.trim($("#channel option:selected").val());
			
			//状态:0 交易未完成 1 交易成功 2 交易失败
			var state = jQuery.trim($("#state option:selected").val());
			
			var currencyUnit = jQuery.trim($("#currencyUnit option:selected").val());
			var beginAmount = $('#amountBegin').val();
			var endAmount =  $('#amountEnd').val();
			var incomeFlag = "";
			var optType  = "";
			var detail = $('#detail').attr('class');
			var income = $('#income').attr('class');
			var out = $('#out').attr('class');
			if(detail=="current"){
				incomeFlag = "";
				optType  = "";
			}else if(income=="current"){
				incomeFlag = "1";
				optType  = "1";
			}else if(out=="current"){
				incomeFlag = "0";
			}

    		return {
				"serialCode":serialCode,
    			"nickName":nickName,
    			"beginDate":beginDate,
    			"endDate":endDate,
    			"channel":channel,
    			"state":state,
				"currencyUnit":currencyUnit,
				"beginAmount":beginAmount,
				"endAmount":endAmount,
				"incomeFlag":incomeFlag,
				"optType":optType,
			}
    	}
    });
    
    module.exports = BusinessListPager
});

