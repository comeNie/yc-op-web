define('app/jsp/order/refundOrderDetail', function(require, exports, module) {
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
	
    
	var refundOrderDetailsPager = Widget.extend({

		attrs : {
			
		},
		Statics : {
			
		},
		// 事件代理
		events : {
			"click #cancel":"_cancel"
		},
		// 重写父类
		setup : function() {
			var _this = this;
			refundOrderDetailsPager.superclass.setup.call(this);
			
			this._queryOrderDetails();
			
		},
		
        _cancel:function(){
        	var isAll =$("#isAll").val();
        	var state =$("#state").val();
        	window.location.href = _base+"/order/back?state="
            + state+'&isAll='+isAll+"&random="+Math.random();
		},
		
		
		_queryOrderDetails:function(){
			var _this = this;
			var param = {};
			param.orderId = $("#orderId").val();
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "加载数据中，请等待...",
				url: _base + "/orderDetail/refundOrdDetails",
				data: param,
				success: function (rs) {
					var orderInfoHtml = $("#orderInfoTempl").render(rs.data);
					$("#date1").html(orderInfoHtml);
					var orderStateChgHtml = $("#orderStateChgTempl").render(rs.data);
					$("#orderStateChgTable").html(orderStateChgHtml);
				}
			});
		}
		
	});
	module.exports = refundOrderDetailsPager;
});