define('app/jsp/sysitembank/sysItemBankList', function (require, exports, module) {
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
            "click #search":"_searchSysItemBankList",
            "click #add":"_add",
            "click #update":"_update",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
        },
    	//重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchSysItemBankList();
    		this._bindLanguageSelect();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	_add:function(){
			window.location.href = _base+"/sysitembank/toAddSysItemBank";
		},
    	_searchSysItemBankList:function(){
			var _this=this;
			var url = _base+"/sysitembank/getSysItemBankPageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"sysItemBankListData",
				data : queryData,
				pageSize: OrderListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#sysItemBankListTemple");
						var htmlOut = template.render(data);
						$("#sysItemBankListData").html(htmlOut);
					}else{
						$("#sysItemBankListData").html("未搜索到信息");
					}
				},
			});
		},
		_getSearchParams:function(){
    		return {
    			"qname":jQuery.trim($("#qname").val()),
    			"site":jQuery.trim($("#site option:selected").val()),
    			"state":jQuery.trim($("#state option:selected").val()),
    			"aditor":jQuery.trim($("#createOperator").val()),
    			"questionType":jQuery.trim($("#questionType option:selected").val()),
    			"langDir":jQuery.trim($("#langugePaire option:selected").val())
    		}
    	},
    	// 下拉 语种方向
		_bindLanguageSelect:function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getLangugeSelect",
				dataType : "json",
				data : {
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var langugeName = d[index].sourceCn+"->"+d[index].targetCn;
						var langugeCode = d[index].duadId;
						$("#langugePaire").append('<option value="'+langugeCode+'">'+langugeName+'</option>');
						$("#updatelangugePaire").append('<option value="'+langugeCode+'">'+langugeName+'</option>');
					})
				}
			});
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
    	_show:function(bid,qname,questionType,site,state){
			var _this= this;
    		$("#bid").val("");
    		$("#updateqname").val("");
    		$("#updatequestionType").val("");
			//弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			
    		$("#updateqname").val(qname);
    		$("#updatequestionType").val(questionType);
    		$("#bid").val(bid);
    		
    		var states = document.getElementsByName("state");
    		for(var i=0;i<states.length;i++){
    			if(state == states[i].value){
    				states[i].checked = true;
    			}
    		}
    		var sites = document.getElementsByName("site");
    		for(var i=0;i<sites.length;i++){
    			if(site == sites[i].value){
    				sites[i].checked = true;
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
			var url = _base + "/sysitembank/updateSysItemBank";
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
    	_delete:function(bid){
			var _this=this;
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "删除数据中，请等待...",
				data: {bid:bid},
				url: _base + "/sysitembank/deleteSysItemBank",
				success: function (rs) {
					 window.location.reload();
				}
			});
		},
		_look:function(bid,questionType){
			window.location.href = _base+"/sysitembank/toQuestionsList?bid="+bid+"&questionType="+questionType;
		},
		_closeDialog:function(){
    		$("#errorMessage").html("");
    		$('#eject-mask').fadeOut(100);
    		$('#add-samll').slideUp(150);
    	}
    });
    
    module.exports = OrderListPager
});

