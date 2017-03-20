define('app/jsp/translatorBill/lspDetailBill', function (require, exports, module) {
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
		/*//事件代理
		events: {
		},*/
		//重写父类
		setup: function () {
			OrderListPager.superclass.setup.call(this);
			// 初始化执行搜索
			this._searchBillList();
		},
		
		_searchBillList:function(){
			var _this=this;
			var url = _base+"/balance/getBillDetailData";
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
						var template = $.templates("#billDetailListTemple");
						var htmlOut = template.render(data);
						$("#orderListData").html(htmlOut);
					}else{
						$("#orderListData").html("未搜索到信息");
					}
				},
			});
		},

		_getSearchParams:function(){
			return {
				"billId":jQuery.trim($("#billID").val()),
			}
		},
	});

	module.exports = OrderListPager
});

