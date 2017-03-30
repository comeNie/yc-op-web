define('app/jsp/user/companyInfo', function(require, exports, module) {
	'use strict';
	var $ = require('jquery');
	var Widget = require('arale-widget/1.2.0/widget');
	var Dialog = require("optDialog/src/dialog");
	var AjaxController = require('opt-ajax/1.0.0/index');
	require("jquery-validation/1.15.1/jquery.validate");
	require("app/util/aiopt-validate-ext");
	require("jsviews/jsrender.min");
	require("jsviews/jsviews.min");
	require("app/util/jsviews-ext");
	require("app/jsp/order/jsviews-order");
	require("webuploader/webuploader");
	require("webuploader/webuploader2.css");
	require("opt-paging/aiopt.pagination");
	require("twbs-pagination/jquery.twbsPagination");
	var ajaxController = new AjaxController();
	
	var showMsg = function(){
    	var d = Dialog({
        		content:"审核成功",
    			okValue: "确定",
    			cancelValue: "关闭",
    			title:"企业审核",
    			ok:function(){
    				window.location.href =_base+"/company/toCompanyListPager";
    			},
    			cancel:function(){
    				d.close();
    			}
    		});
    	d.showModal();
    };
	
	var cache = null;
	
    
	var CompanyInfoDetailsPager = Widget.extend({

		attrs : {
			
		},
		Statics : {
			DEFAULT_PAGE_SIZE: 10
		},
		// 事件代理
		events : {
			"click [id='auditPass']":"_auditPass",
			"blur [id='corporateDiscount']":"_checkCorporateDiscount",
			"blur [id='statementDate']":"_checkStatementDate",
			"blur [id='settlementPeriod']":"_checkSettlementPeriod"
		},
		// 重写父类
		setup : function() {
			var _this = this;
			CompanyInfoDetailsPager.superclass.setup.call(this);
			this._queryCompanyDetails();
			this._searchAllCompanyList();
			/*var formValidator=_this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});*/
		},
		_searchAllCompanyList:function(){
			var _this=this;
			var url = _base+"/company/getCompanyUsersList";
			$("#companyUsersPagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"companyUsersListData",
				data : {
					'companyId':companyId
				},
				pageSize: CompanyInfoDetailsPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#companyUserListTemple");
						var htmlOut = template.render(data);
						$("#companyUsersListData").html(htmlOut);
					}else{
						$("#companyUsersListData").html("未搜索到信息");
					}
				},
			});
		},
		_queryCompanyDetails:function(){
			var _this = this;
			var param = {};
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "加载数据中，请等待...",
				url: _base + "/company/getCompanyDetail",
				data: {
					"userId":userId,
					"usersource":usersource,
					"createTime":createTime
				},
				success: function (rs) {
					var orderInfoHtml = $("#orderInfoTempl").render(rs.data);
					$("#date1").html(orderInfoHtml);
					var orderStateChgHtml = $("#orderStateChgTempl").render(rs.data);
					$("#orderStateChgTable").html(orderStateChgHtml);
					//获取stateCheck
					var stateCheck=$("#stateCheck").val();
					if(stateCheck=="" || stateCheck==null){
						$("#checkemarkId").hide();
					}
				
				}
			});
		},
		_checkCorporateDiscount:function(){
			var flag = true;
			var corporateDiscount= $("#corporateDiscount").val();
			if(corporateDiscount==null||corporateDiscount==""){
				flag = false;
				$("#discountErrMsg").show();
				$("#discountText").text("请输入折扣");
			}else{
				var reg = /^([1-9\d{1,2}|100])$/;
				if(!reg.test(corporateDiscount)){
					flag = false;
					$("#discountErrMsg").show();
					$("#discountText").text("请输入100以内整数");
				}else{
					$("#discountErrMsg").hide();
				}
			}
			return flag;
		},
		_checkStatementDate:function(){
			var flag = true;
			var statementDate= $("#statementDate").val();
			if(statementDate==null||statementDate==""){
				flag = false;
				$("#statementDateErrMsg").show();
				$("#statementDateText").text("请输入账单日");
			}else{
				var reg = /^\d+$/;
				if(!reg.test(statementDate)){
					flag = false;
					$("#statementDateErrMsg").show();
					$("#statementDateText").text("请输入31以内的整数");
				}else{
					if(statementDate>31){
						flag = false;
						$("#statementDateErrMsg").show();
						$("#statementDateText").text("请输入31以内的整数");
					}else{
						$("#statementDateErrMsg").hide();
					}
				}
			}
			return flag;
		},
		_checkSettlementPeriod:function(){
			var flag = true;
			var settlementPeriod= $("#settlementPeriod").val();
			if(settlementPeriod==null||settlementPeriod==""){
				flag = false;
				$("#settlementPeriodErrMsg").show();
				$("#settlementPeriodText").text("请输入结算周期");
			}
			var reg = /^\d+$/;
			if(!reg.test(settlementPeriod)){
				flag = false;
				$("#settlementPeriodErrMsg").show();
				$("#settlementPeriodText").text("请输入整数");
			}else{
				$("#settlementPeriodErrMsg").hide();
			}
			return flag;
		},
		_auditPass:function(){
			var CFlag = this._checkCorporateDiscount();
			var	SFlag =	this._checkStatementDate();
			var PFlag = this._checkSettlementPeriod();
			if(CFlag&&SFlag&&PFlag){
				var settlingAccounts = $("#settlingAccounts").val();
				var corporateDiscount = $("#corporateDiscount").val();
				var statementDate = $("#statementDate").val();
				var settlementPeriod = $("#settlementPeriod").val();
				var _this = this;
				ajaxController.ajax({
					type: "post",
					processing: true,
					message: "加载数据中，请等待...",
					url: _base + "/company/auditCompanyInfo",
					data: {
						"settlingAccounts":settlingAccounts,
						"companyId":companyId,
						"corporateDiscount":corporateDiscount,
						"statementDate":statementDate,
						"settlementPeriod":settlementPeriod,
						"state":1,
						"companyName":companyName
					},
					success: function (rs) {
						if(rs.data){
							 showMsg();
						}else{
							alert("失败了")
						}
					}
				});
			}
			
		}
		
	});
	module.exports = CompanyInfoDetailsPager;
});