define('app/jsp/translator/translatorInfoList', function (require, exports, module) {
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
    var TranslatorInfoListPager = Widget.extend({
    	
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
    		TranslatorInfoListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchBillList();
    		/*var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});*/
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
			var url = _base+"/translator/getTranslatorPageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"translatorListData",
				data : queryData,
				pageSize: TranslatorInfoListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#translatorListTemple");
						var htmlOut = template.render(data);
						$("#translatorListData").html(htmlOut);
					}else{
						$("#translatorListData").html("未搜索到信息");
					}
				},
			});
		},
		
		_getSearchParams:function(){
			var stateBill="";
			var state="";
			var nickName = $("#nickName").val();
			var companyName = $("#companyName").val();
			var mobilephone = $("#mobilephone").val();
			var checkName = $("#checkName").val();
			var userSource = $("#userSource").val()
			var domestic = $('#domestic').attr('class');
			var foreign = $('#foreign').attr('class');
			//待审核
			if(domestic="current1" && domestic!=""){
				state = 0;
			}else if(foreign="current1" && foreign!=""){
				state=  1;//企业列表
			}
    		return {
    			"nickName":jQuery.trim($("#nickName").val()),
    			"companyName":jQuery.trim($("#companyName").val()),
    			"mobilephone":jQuery.trim($("#mobilephone").val()),
    			"checkName":jQuery.trim($("#checkName").val()),
    			"state":state,
    			"userSource":userSource,
			}
    	},
    	_toCompanyDetail:function(companyId,userId,usersource,createTime){
    		window.location.href = _base+"/company/toCompanyDetailPager?userId="+userId+"&usersource="+usersource+"&createTime="+createTime+"&companyId="+companyId;
    	},
    	_toCompanyAudit:function(companyId,companyName,userId,usersource,createTime){
    		window.location.href = _base+"/company/toCompanyAuditPager?userId="+userId+"&usersource="+usersource+"&createTime="+createTime+"&companyId="+companyId+"&companyName="+companyName;
    	}
    });
    
    module.exports = TranslatorInfoListPager
});

