define('app/jsp/sysduad/sysDuadList', function (require, exports, module) {
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
    // 实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    // 定义页面组件类
    var OrderListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	// 属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 20
    	},
    	// 事件代理
    	events: {
			// 查询
            "click #search":"_searchSysDuadList",
            "click #add":"_add",
            "click #update":"_update",
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog",
        },
    	// 重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchSysDuadList();
    		this._bindLanguageSelect();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
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
						if(d[index].language == '2'){
							var langugeName = "英文"+"->"+d[index].sourceCn+"->"+d[index].targetCn;
						}else{
							var langugeName = "中文"+"->"+d[index].sourceCn+"->"+d[index].targetCn;
						}
						var langugeCode = d[index].duadId;
						$("#langugePaire").append('<option value="'+langugeCode+'">'+langugeName+'</option>');
					})
				}
			});
		},
    	_add:function(){
			window.location.href = _base+"/sysduad/toAddDuad";
		},

		_searchSysDuadList:function(){
			var _this=this;
			var url = _base+"/sysduad/getSysDuadPageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"sysDuadListData",
				data : queryData,
				pageSize: OrderListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#sysDuadListTemple");
						var htmlOut = template.render(data);
						$("#sysDuadListData").html(htmlOut);
					}else{
						$("#sysDuadListData").html("未搜索到信息");
					}
				},
			});
		},
		
		_languageDir:function(){
			
		},
		
		_getSearchParams:function(){
    		return {
    			"language":jQuery.trim($("#language option:selected").val()),
    			"site":jQuery.trim($("#site option:selected").val()),
    			"state":jQuery.trim($("#state option:selected").val()),
    			"createOperator":jQuery.trim($("#createOperator").val()),
    			"duadId":jQuery.trim($("#langugePaire option:selected").val())
    		}
    	},
    	_show:function(duadId,language,site,sourceCn,targetCn,ordinary,ordinaryUrgent,professional,professionalUrgent,publish,publishUrgent,sort,state,ordinaryDollar,ourgentDollar,professionalDollar,purgentDollar,publishDollar,puburgentDollar){
			var _this= this;
    		$("#language").val("");
    		$("#duadId").val("");
    		$("#sourceCn").val("");
    		$("#targetCn").val("");
    		$("#ordinary").val("");
    		$("#ordinaryUrgent").val("");
    		$("#professional").val("");
    		$("#professionalUrgent").val("");
    		$("#publish").val("");
    		$("#publishUrgent").val("");
    		$("#sort").val("");
    		$("#ordinaryDollar").val("");
    		$("#ourgentDollar").val("");
    		$("#professionalDollar").val("");
    		$("#purgentDollar").val("");
    		$("#publishDollar").val("");
    		$("#puburgentDollar").val("");
			// 弹出框展示
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			$("#language").val(language);
    		$("#duadId").val(duadId);
    		$("#sourceCn").val(sourceCn);
    		$("#targetCn").val(targetCn);
    		$("#ordinary").val(ordinary);
    		$("#ordinaryUrgent").val(ordinaryUrgent);
    		$("#professional").val(professional);
    		$("#professionalUrgent").val(professionalUrgent);
    		$("#publish").val(publish);
    		$("#publishUrgent").val(publishUrgent);
    		$("#sort").val(sort);
    		$("#ordinaryDollar").val(ordinaryDollar);
    		$("#ourgentDollar").val(ourgentDollar);
    		$("#professionalDollar").val(professionalDollar);
    		$("#purgentDollar").val(purgentDollar);
    		$("#publishDollar").val(publishDollar);
    		$("#puburgentDollar").val(puburgentDollar);
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
			var url = _base + "/sysduad/updateSysDuad";
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
		_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    				"site":{
    					required:true
    				},
    				"sourceCn":{
    					required:true
    				},
    				"targetCn":{
    					required:true
    				},
    				"ordinary":{
    					required:true
    				},
    				"ordinaryUrgent":{
    					required:true
    				},
    				"professional":{
    					required:true
    				},
    				"professionalUrgent":{
    					required:true
    				},
    				"publish":{
    					required:true
    				},
    				"publishUrgent":{
    					required:true
    				},
    				"ordinaryDollar":{
    					required:true
    				},
    				"ourgentDollar":{
    					required:true
    				},
    				"professionalDollar":{
    					required:true
    				},
    				"purgentDollar":{
    					required:true
    				},
    				"publishDollar":{
    					required:true
    				},
    				"puburgentDollar":{
    					required:true
    				}
    			},
    			messages: {
    				"site":{
    					required:"请选择站点"
    				},
    				"sourceCn":{
    					required:"请输入领域名称"
    				},
    				"targetCn":{
    					required:"请输入领域名称"
    				},
    				"ordinary":{
    					required:"请输入普通级翻译价格"
    				},
    				"ordinaryUrgent":{
    					required:"请输入加急普通级翻译价格"
    				},
    				"professional":{
    					required:"请输入专业级翻译价格"
    				},
    				"professionalUrgent":{
    					required:"请输入加急专业级翻译价格"
    				},
    				"publish":{
    					required:"请输入出版级翻译价格"
    				},
    				"publishUrgent":{
    					required:"请输入加急出版级翻译价格"
    				},
    				"ordinaryDollar":{
    					required:"请输入普通级翻译美元价格"
    				},
    				"ourgentDollar":{
    					required:"请输入加急普通级翻译美元价格"
    				},
    				"professionalDollar":{
    					required:"请输入专业级翻译美元价格"
    				},
    				"purgentDollar":{
    					required:"请输入加急专业级翻译美元价格"
    				},
    				"publishDollar":{
    					required:"请输入出版级翻译美元价格"
    				},
    				"puburgentDollar":{
    					required:"请输入加急出版级翻译美元价格"
    				}
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

