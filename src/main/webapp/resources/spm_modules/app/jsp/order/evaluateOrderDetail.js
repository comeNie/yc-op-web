define('app/jsp/order/evaluateOrderDetail', function(require, exports, module) {
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
	
    
	var evaluteOrderDetailsPager = Widget.extend({

		attrs : {
			
		},
		Statics : {
			
		},
		// 事件代理
		events : {
			"click #cancel":"_cancel",
			"click #save":"_update"
		},
		// 重写父类
		setup : function() {
			var _this = this;
			evaluteOrderDetailsPager.superclass.setup.call(this);
			
			this._queryOrderDetails();
			
		},
		
        _cancel:function(){
        	var isAll =$("#isAll").val();
        	var state =$("#state").val();
        	window.location.href = _base+"/order/back?state="
            + state+'&isAll='+isAll+"&random="+Math.random();
		},
		_update:function(){
			var _this=this;
			var orderId = $("#orderId").val();
			var remark = $("#remark").val();
			var state = $('input:radio:checked').val();
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/updateEvaluate",
				dataType : "json",
				data : {
					orderId:orderId,
					state:state,
					remark:remark
				},
				message : "正在加载数据..",
				success : function(data) {
					_this._cancel();
				}
			});
		},
		
		_queryOrderDetails:function(){
			var _this = this;
			var param = {};
			param.orderId = $("#orderId").val();
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "加载数据中，请等待...",
				url: _base + "/evaluteOrdDetail",
				data: param,
				success: function (rs) {
					var orderInfoHtml = $("#orderInfoTempl").render(rs.data);
					$("#date1").html(orderInfoHtml);
					var orderStateChgHtml = $("#orderStateChgTempl").render(rs.data);
					$("#orderStateChgTable").html(orderStateChgHtml);
					//状态赋值
					var state = rs.data.evaluateVo.state;
					if(state=='0'){
						//隐藏
						$("#hiddenId").attr('checked', 'checked');
					}else{
						$("#showId").attr('checked', 'checked');
					}
				}
			});
		}
		
	});
	module.exports = evaluteOrderDetailsPager;
});