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

	var ajaxController = new AjaxController();
	
	var cache = null;
	
    
	var CompanyInfoDetailsPager = Widget.extend({

		attrs : {
			
		},
		Statics : {
			
		},
		// 事件代理
		events : {
			"click [id='auditPass']":"_auditPass",
		},
		// 重写父类
		setup : function() {
			var _this = this;
			CompanyInfoDetailsPager.superclass.setup.call(this);
			this._queryCompanyDetails();
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
		_auditPass:function(){
			var settlingAccounts = $("#settlingAccounts").val();
			var corporateDiscount = $("#corporateDiscount").val();
			var statementDate = $("#statementDate").val();
			var settlementPeriod = $("#settlementPeriod").val();
			alert(statementDate);
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
						alert("成功了");
					}else{
						alert("失败了")
					}
				}
			});
		}
		
	});
	module.exports = CompanyInfoDetailsPager;
});