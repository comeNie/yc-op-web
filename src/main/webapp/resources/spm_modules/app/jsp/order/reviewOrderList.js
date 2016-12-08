define('app/jsp/order/reviewOrderList', function (require, exports, module) {
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
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    require('bootstrap/js/modal');
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    var showErrorDialog = function(error){
    	var d = Dialog({
			content:error,
			icon:'fail',
			okValue: '确 定',
			title: '提示',
			ok:function(){
				this.close();
			}
		});
		d.showModal();
    }
	
	var showSuccessDialog = function(error){
    	var d = Dialog({
			content:error,
			icon:'success',
			okValue: '确 定',
			title: '提示',
			ok:function(){
				this.close();
			}
		});
		d.showModal();
    }
	
    //定义页面组件类
    var ReviewOrdListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//查询
            "click #search":"_searchOrderList",
            "click #export":"_export",
            "click #batchAdopt":"_batchAdoptReviewOrder"
            
        },
    	//重写父类
    	setup: function () {
    		var _this = this;
    		ReviewOrdListPager.superclass.setup.call(this);
    		
    		//搜索区高级搜索 点击展开
    		$(document).on("click",".sos a",function () {	  
    			  $("#selectDiv").slideToggle(100); 
    		});
    		//日期控件
			$(document).on("click",".fa-calendar",function(){
				var calInput = $(this).parent().prev();
				var timeId = calInput.attr('id');
				WdatePicker({el:timeId,readOnly:true,dateFmt:'yyyy-MM-dd'});
			});
			
			$("#data-table").on("change",".single",function(){	
				if(this.checked){
					var checkedNum = 0
					$(".single").each(function(){
						if(this.checked){
							checkedNum = checkedNum + 1;
						}
					});
					
					if($(".single").length==checkedNum){
						$(".all").prop("checked",true);
					}
					
				}else{
					$(".all").prop("checked",false);
				}
			});
			$("#data-table").on("change",".all",function(){
				if(this.checked){	
					$(".single").each(function(){
						$(this).prop("checked",true);
					});
				}else{
					$(".single").each(function(){
						$(this).prop("checked",false);
					});
				}
			});
			//通过按钮事件
			$("#orderListData").on("click",".adopt",function(){
				var $this = $(this);
				var $tr = $this.parent("td").parent("tr");
				var param = {};
				var $tds = $tr.find("td");
				param.orderIds = $tds.eq(0).find("input").eq(0).val();
				_this.adoptReviewOrder(param);
			});
			
			//驳回按钮事件
			$("#orderListData").on("click",".reject",function(){
				var $this = $(this);
				var $tr = $this.parent("td").parent("tr");
				var param = {};
				var $tds = $tr.find("td");
				param.orderIds = $tds.eq(0).find("input").eq(0).val();
				_this.rejectReviewOrder(param);
			});
			
    		// 初始化执行搜索
    		this._searchOrderList();
    		this._bindChlIdSelect();
    		this._bindOrdTypeSelect();
    		this._bindOrdLeveleSelect();
    		this._bindLanguageSelect();
    	},
		// 下拉 订单级别
		_bindOrdLeveleSelect : function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"ORDER_LEVEL",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#orderLevel").append('<option value="'+paramCode+'">'+paramName+'</option>');
					})
				}
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
						var langugeName = d[index].transTypeName+"->"+d[index].sourceCn+"->"+d[index].targetCn;
						var langugeCode = d[index].duadId;
						$("#langugePaire").append('<option value="'+langugeCode+'">'+langugeName+'</option>');
					})
				}
			});
		},
		// 下拉订单类型（对应库中的翻译类型）
		_bindOrdTypeSelect:function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"TRANSLATE_TYPE",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#orderType").append('<option value="'+paramCode+'">'+paramName+'</option>');
					})
				}
			});
		},
		// 下拉 订单来源
		_bindChlIdSelect : function() {
			var this_=this;
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/getSelect",
				dataType : "json",
				data : {
					paramCode:"CHL_ID",
					typeCode:"ORD_ORDER"
				},
				message : "正在加载数据..",
				success : function(data) {
					var d=data.data;
					$.each(d,function(index,item){
						var paramName = d[index].columnDesc;
						var paramCode = d[index].columnValue;
						$("#orderSource").append('<option value="'+paramCode+'">'+paramName+'</option>');
					})
				}
			});
		},
		_export:function(){
			var _this=this;
			var orderTimeS=jQuery.trim($("#orderTimeBegin").val());
			var orderTimeE=jQuery.trim($("#orderTimeEnd").val());
			var lockTimeS=jQuery.trim($("#lockTimeBegin").val());
			var lockTimeE=jQuery.trim($("#lockTimeEnd").val());
			
			if(orderTimeS=="" || orderTimeS==null){
				orderTimeS="";
			}else{
				orderTimeS= new Date( Date.parse( $("#orderTimeBegin").val().replace(/-/g,"/")+" 00:00:00" ) ).getTime();
			}
			if(orderTimeE=="" || orderTimeE==null){
				orderTimeE="";
			}else{
				orderTimeE= new Date( Date.parse( $("#orderTimeEnd").val().replace(/-/g,"/")+" 23:59:59" ) ).getTime();
			}
			//领取时间
			if(lockTimeS=="" || lockTimeS==null){
				lockTimeS="";
			}else{
				lockTimeS= new Date( Date.parse( $("#lockTimeBegin").val().replace(/-/g,"/")+" 00:00:00" ) ).getTime();
			}
			if(lockTimeE=="" || lockTimeE==null){
				lockTimeE="";
			}else{
				lockTimeE= new Date( Date.parse( $("#lockTimeEnd").val().replace(/-/g,"/")+" 23:59:59" ) ).getTime();
			}
			var userName=jQuery.trim($("#nickName").val());
			var chlId=jQuery.trim($("#orderSource option:selected").val());
			var translateType=jQuery.trim($("#orderType option:selected").val());
			var langungePaire=jQuery.trim($("#langugePaire option:selected").val());
			var orderPageId=jQuery.trim($("#orderId").val());
			var interperName = jQuery.trim($("#interperName").val());
			var orderLevel = jQuery.trim($("#orderLevel option:selected").val());
			window.location.href=_base+'/order/exportReviewOrderList?orderTimeS='+orderTimeS+'&orderTimeE='+orderTimeE+'&lockTimeS='+lockTimeS+
			'&userName='+userName+'&chlId='+chlId+'&translateType='+translateType+'&langungePaire='+langungePaire+
		    '&orderPageId='+orderPageId+'&lockTimeE='+lockTimeE+'&interperName='+interperName+'&orderLevel='+orderLevel;
		},
		_batchAdoptReviewOrder:function(){
			var _this = this;
			var param = {};
			var orderIds = "";
			$(".single").each(function(){
				if(this.checked){
					orderIds = orderIds+"," + this.value;
				}
			});
			if(orderIds==""){
				showErrorDialog("请至少选择一条订单信息");
				return;
			}
			param.orderIds = orderIds.substring(1);
			var d = Dialog({
				content:"是否批量审核通过这些订单？",
				okValue: '通过',
				title: '审核',
				ok:function(){
					param.state = '41';
					_this.handReviewOrder(param);
				},
				cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
			
		},
		rejectReviewOrder:function(param){
			var _this = this;
			var d = Dialog({
				content:'<textarea id="reasonDesc" style="width:200px;" class="int-text"></textarea>',
				padding: 0,
				okValue: '驳回',
				title: '驳回原因:',
				ok:function(){
					param.reasonDesc = $("#reasonDesc").val();
					param.state = '42';
					_this.handReviewOrder(param);
				},
				cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
		},
		adoptReviewOrder:function(param){
			var _this = this;
			var d = Dialog({
				content:"是否审核通过该订单？",
				okValue: '通过',
				title: '审核',
				ok:function(){
					param.state = '41';
					_this.handReviewOrder(param);
				},
				cancelValue: '取消',
			    cancel: function () {}
			});
			d.showModal();
		},
		handReviewOrder:function(param){
			var _this=this;
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "保存数据中，请等待...",
				url: _base + "/order/handReviewOrder",
				data: param,
				success: function (data) {
					if(data.data=="00"){
						//如果通过调到待确认列表
						window.location.href=_base+"/toTbcOrderList";
					}else if(data.data=="11"){
						//如果通过调到翻译中列表
						window.location.href=_base+"/toTranslateOrderList";
					}else{
						_this._searchOrderList();
					}
				}
			});
		},
		_searchOrderList:function(){
			var _this=this;
			var url = _base+"/order/getReviewOrderList";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"orderListData",
				data : queryData,
				pageSize: ReviewOrdListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#orderListTemple");
						var htmlOut = template.render(data);
						$("#orderListData").html(htmlOut);
					}else{
						$("#orderListData").html("未搜索到信息");
					}
				},
			});
		},
	
		_getSearchParams:function(){
			var orderTimeS = $("#orderTimeBegin").val();
			var orderTimeE = $("#orderTimeEnd").val();
			var lockTimeS =  $("#lockTimeBegin").val();
			var lockTimeE =  $("#lockTimeEnd").val();
			if(orderTimeS=="" || orderTimeS==null){
				orderTimeS=null;
			}else{
				orderTimeS= new Date( Date.parse( $("#orderTimeBegin").val().replace(/-/g,"/")+" 00:00:00" ) ).getTime();
			}
			if(orderTimeE=="" || orderTimeE==null){
				orderTimeE=null;
			}else{
				orderTimeE= new Date( Date.parse( $("#orderTimeEnd").val().replace(/-/g,"/")+" 23:59:59" ) ).getTime();
			}
			//领取时间
			if(lockTimeS=="" || lockTimeS==null){
				lockTimeS=null;
			}else{
				lockTimeS= new Date( Date.parse( $("#lockTimeBegin").val().replace(/-/g,"/")+" 00:00:00" ) ).getTime();
			}
			if(lockTimeE=="" || lockTimeE==null){
				lockTimeE=null;
			}else{
				lockTimeE= new Date( Date.parse( $("#lockTimeEnd").val().replace(/-/g,"/")+" 23:59:59" ) ).getTime();
			}
			
    		return {
    			"orderTimeS":orderTimeS,
    			"orderTimeE":orderTimeE,
    			"lockTimeS":lockTimeS,
    			"lockTimeE":lockTimeE,
    			"userName":jQuery.trim($("#nickName").val()),
    			"chlId":$("#orderSource option:selected").val(),
    			"translateType":$("#orderType option:selected").val(),
    			"langungePaire":jQuery.trim($("#langugePaire option:selected").val()),
    			"orderPageId":jQuery.trim($("#orderId").val()),
    			"interperName":jQuery.trim($("#interperName").val()),
    			"orderLevel":$("#orderLevel option:selected").val()
    		}
    	}
		
    });
    
    module.exports = ReviewOrdListPager
});

