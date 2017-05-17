define('app/jsp/sysitembank/sysQuestionsChoiceList', function (require, exports, module) {
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
		//事件代理
		events: {
			"click #add":"_add",
			"click #update":"_update",
			"click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
		},
		//重写父类
		setup: function () {
			OrderListPager.superclass.setup.call(this);
			// 初始化执行搜索
			this._searchQuestionsList();
		},
		_add:function(){
			var bid = jQuery.trim($("#bid").val());
			var questionType = jQuery.trim($("#questionType").val());
			window.location.href = _base+"/sysquestions/toAddSysQuestionsChoice?bid="+bid+"&questionType="+questionType;
		},
		_searchQuestionsList:function(){
			var _this=this;
			var url = _base+"/sysquestions/getSysQuestionsPageData";
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
						var template = $.templates("#questionsListTemple");
						var htmlOut = template.render(data);
						$("#orderListData").html(htmlOut);
					}else{
						$("#orderListData").html("未搜索到信息");
					}
				}
			});
		},
		_show:function(qid,choiceQuestion,optiona,optionb,optionc,optiond,answer,status){
			var _this= this;
    		$("#qid").val("");
    		$("#updatechoiceQuestion").val("");
    		$("#updateoptiona").val("");
    		$("#updateoptionb").val("");
    		$("#updateoptionc").val("");
    		$("#updateoptiond").val("");
    		$("#updateanswer").val("");
			//弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			$("#qid").val(qid);
    		$("#updatechoiceQuestion").val(choiceQuestion);
    		$("#updateoptiona").val(optiona);
    		$("#updateoptionb").val(optionb);
    		$("#updateoptionc").val(optionc);
    		$("#updateoptiond").val(optiond);
    		$("#updateanswer").val(answer);
    		var states = document.getElementsByName("status");
    		for(var i=0;i<states.length;i++){
    			if(status == states[i].value){
    				states[i].checked = true;
    			}
    		}
		},
		_update:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
			var param = $("#dataForm").serializeArray();
			var url = _base + "/sysquestions/updateSysQuestions";
			ajaxController.ajax({
				type: "post",
				dataType:"json",
				processing: true,
				message: "保存数据中，请等待...",
				url: url,
				data: param,
				success: function (rs) {
					window.location.reload();
				}
			});
		},
		_delete:function(qid){
			var _this=this;
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "删除数据中，请等待...",
				data: {qid:qid},
				url: _base + "/sysquestions/deleteSysQuestions",
				success: function (rs) {
					 window.location.reload();
				}
			});
		},
		_getSearchParams:function(){
			return {
				"bid":jQuery.trim($("#bid").val()),
				"qid":jQuery.trim($("#qidUpdate").val()),
				"questionType":jQuery.trim($("#questionType").val())
			}
		},
		_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    			},
    			messages: {
    			}
    		});
    		return formValidator;
    	},
		_closeDialog:function(){
    		$("#errorMessage").html("");
    		$('#eject-mask').fadeOut(100);
    		$('#add-samll').slideUp(150);
    	}
	});

	module.exports = OrderListPager
});

