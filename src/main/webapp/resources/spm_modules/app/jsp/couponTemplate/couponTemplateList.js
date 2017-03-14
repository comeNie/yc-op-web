define('app/jsp/couponTemplate/couponTemplateList', function (require, exports, module) {
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
    		DEFAULT_PAGE_SIZE: 20
    	},
    	//事件代理
    	events: {
			//查询
            "click #search":"_searchCouponTemplateList"
            /*"click #export":"_export",*/
            
        },
    	//重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchCouponTemplateList();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},


		/*_export:function(){
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
		},*/

    	_searchCouponTemplateList:function(){
			var _this=this;
			var url = _base+"/balance/getCouponTemplatePageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"couponTemplateListData",
				data : queryData,
				pageSize: OrderListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#couponTemplateListTemple");
						var htmlOut = template.render(data);
						$("#couponTemplateListData").html(htmlOut);
					}else{
						$("#couponTemplateListData").html("未搜索到信息");
					}
				},
			});
		},
		
		_getSearchParams:function(){
			/*var stateBill="";
			var flag="";
			var orderTimeS = $("#billTimeBegin").val();
			var orderTimeE = $("#billTimeEnd").val();
			var wait = $('#waitHandle').attr('class');
			var refund = $('#refundId').attr('class');
			var domestic = $('#domestic').attr('class');
			var foreign = $('#foreign').attr('class');

			if(domestic="current1" && domestic!=""){
				flag = 0;
			}else if(foreign="current1" && foreign!=""){
				flag=  1;//待审核
			}
			if(wait="current" && wait!=""){
				stateBill = 2;
			}else if(refund="current" && refund!=""){
				stateBill=  1;//待审核
			}*/
    		return {
    			"couponName":jQuery.trim($("#couponName").val()),
    			"faceValue":jQuery.trim($("#faceValue").val()),
    			"usedScene":jQuery.trim($("#usedScene option:selected").val()),
    			"status":jQuery.trim($("#status option:selected").val()),
    			"currencyUnit":jQuery.trim($("#currencyUnit option:selected").val()),
    			"createOperator":jQuery.trim($("#createOperator").val())
    		}
    	}
    });
    
    module.exports = OrderListPager
});

