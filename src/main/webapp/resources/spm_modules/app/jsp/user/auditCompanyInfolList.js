define('app/jsp/user/auditCompanyInfolList', function (require, exports, module) {
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
    var CompanyListPager = Widget.extend({
    	
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
			"click #foreign":"_searchAllCompanyList",
			//查询
            "click #search":"_searchBillList",
            "click #update":"_updatePayState",
            "click #export":"_export",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog",
        },
    	//重写父类
    	setup: function () {
    		CompanyListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchBillList();
    		
    	},
		_export:function(){
			var _this=this;
			var stateBill="";
			var flag="";
			var stateBill="";
			var nickName = $("#nickName").val();
			var companyName = $("#companyName").val();
			var mobilephone = $("#mobilephone").val();
			var checkName = $("#checkName").val();
			var userSource = $("#userSource").val()
			var domestic = $('#domestic').attr('class');
			var foreign = $('#foreign').attr('class');
			var state = "0";
			window.location.href=_base+'/company/export?nickName='+nickName+'&companyName='+companyName+'&mobilephone='+mobilephone+
			'&checkName='+checkName+'&userSource='+userSource+"&flag='audit'"+"&state="+state;
		},
		_checkNickNameValue:function(){
			var nickName = $("#nickName").val();
			var flag = true;
			if(nickName!=null&&nickName!=""){
				var re = /^[\-_0-9a-zA-Z\u4e00-\u9fa5]{1,24}$/;
				if(!re.test(value)){
					$("#nickNameErrMsg").show();
					$("#nickNameErrText").text("昵称为中英文下划线或减号1-24个字符");
					flag = false;
				}else{
					$("#nickNameErrMsg").hide();
				}
			}
		},
		_checkCompanyNameValue:function(){
			var companyName = $("#companyName").val();
			if(companyName!=null&&companyName!=""){
				var cLength = companyName.length;
				if(cLength>50){
					
				}
				
			}
		},
		_searchBillList:function(){
			var _this=this;
			var url = _base+"/company/getList";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"companyListData",
				data : queryData,
				pageSize: CompanyListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#companyListTemple");
						var htmlOut = template.render(data);
						$("#companyListData").html(htmlOut);
					}else{
						$("#companyListData").html("未搜索到信息");
					}
				},
			});
		},
		_searchAllCompanyList:function(){
			var _this=this;
			window.location.href = _base+"/company/toAllCompanyListPager";
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
			state = 0;
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
    
    module.exports = CompanyListPager
});

