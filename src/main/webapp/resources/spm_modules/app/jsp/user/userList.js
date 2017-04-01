define('app/jsp/user/userList', function (require, exports, module) {
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
    
    var showSuccessDialog = function(error){
    	var d = Dialog({
			content:error,
			icon:'success',
			okValue: '确 定',
			title: '提示',
			ok:function(){
				this.close();
				window.location.href=_base+"/user/toUserList?random="+Math.random();
			}
		});
		d.showModal();
    };
    var SendMessageUtil = require("app/util/sendMessage");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var UserListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		 "click #showQuery":"_showQueryInfo",
    		//查询
            "click #search":"_searchUserList",
            "click #update":"_saveGrowthValue",
            /*"click #export":"_export",*/
            "click #add-close":"_closeDialog",
            "click #colseImage":"_closeDialog"
            
        },
    	//重写父类
    	setup: function () {
    		UserListPager.superclass.setup.call(this);
    		// 初始化执行搜索
    		this._searchUserList();
    		var formValidator=this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	_showQueryInfo: function(){
			//展示查询条件
			var info= $("#selectDiv").is(":hidden"); //是否隐藏
		    if(info==true){
		    	$("#selectDiv").show();
		    }else{
		    	$("#selectDiv").hide();
		    }
		},
		_searchUserList:function(){
			var _this=this;
			var url = _base+"/user/getUserPageData";
			var queryData = this._getSearchParams();
			$("#pagination").runnerPagination({
				url:url,
				method: "POST",
				dataType: "json",
				messageId:"showMessage",
				renderId:"userListData",
				data : queryData,
				pageSize: UserListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#userListTemple");
						var htmlOut = template.render(data);
						$("#userListData").html(htmlOut);
					}else{
						$("#userListData").html("未搜索到信息");
					}
				},
			});
		},
		_initValidate:function(){
    		var formValidator=$("#dataForm").validate({
    			rules: {
    				type: {
    					required: true
    				},
    				griwthValue: {
        				required: true,
        				max:99999
        			},
        			griwthResource: {
        				required: true,
        				maxlength:30
        			}
    			},
    			messages: {
    				type: {
    					required: "请选择操作!",
    					maxlength:"最大长度不能超过{0}"
    				},
    				griwthValue: {
    					required: "请输入数量!",
    					maxlength:"最大数量不能超过{0}"
    				},
    				griwthResource: {
    					required: "请输入操作原因!",
    					maxlength:"最大长度不能超过{0}"
    				}
    			}
    		});
    		return formValidator;
    	},
    	_closeDialog:function(){
    		$("#errorMessage").html("");
    		$('#eject-mask').fadeOut(100);
    		$('#add-samll').slideUp(150);
    	},
		_getSearchParams:function(){
			var nickname = $("#nickName").val().trim();
			var mobilePhone = $("#mobilePhone").val();
			var isTranslator = $("#userType").val();
			var registTimeStart = $("#registTimeStart").val();
			var registTimeEnd = $("#registTimeEnd").val();
			var loginTimeBegin = $("#loginTimeBegin").val();
			var loginTimeEnd = $("#loginTimeEnd").val();
			if(registTimeStart=="" || registTimeStart==null){
				registTimeStart="";
			}else{
				registTimeStart= new Date( Date.parse( $("#registTimeStart").val().replace(/-/g,"/"))).getTime();
			}
			if(registTimeEnd=="" || registTimeEnd==null){
				registTimeEnd="";
			}else{
				registTimeEnd= new Date( Date.parse( $("#registTimeEnd").val().replace(/-/g,"/")) ).getTime();
			}
			if(loginTimeBegin=="" || loginTimeBegin==null){
				loginTimeBegin="";
			}else{
				loginTimeBegin= new Date( Date.parse( $("#loginTimeBegin").val().replace(/-/g,"/")) ).getTime();
			}
			if(loginTimeEnd=="" || loginTimeEnd==null){
				loginTimeEnd="";
			}else{
				loginTimeEnd= new Date( Date.parse( $("#loginTimeEnd").val().replace(/-/g,"/")) ).getTime();
			}
    		return {
    			"nickname":nickname,
    			"mobilePhone":mobilePhone,
    			"usersource":jQuery.trim($("#userSource option:selected").val()),
    			"safetyLevel":jQuery.trim($("#safetyLevel option:selected").val()),
    			"isTranslator":jQuery.trim($("#userType option:selected").val()),
    			"isCompany":jQuery.trim($("#isCompany option:selected").val()),
    			"state":jQuery.trim($("#userState option:selected").val()),
    			"registTimeStart":registTimeStart,
    			"registTimeEnd":registTimeEnd
    		}
    	},
    	//弹出框
    	_popUp:function(userId){
    		var _this= this;
    		$("#dataForm [name=griwthValue]").val("");
    		$("#dataForm [name=griwthResource]").val("");
    		var formValidator=_this._initValidate();
			formValidator.form();
			$('#eject-mask').fadeIn(100);
			$('#add-samll').slideDown(200);
			$("#dataForm [name=userId]").val(userId);
    	},
    	_saveGrowthValue:function(){
    		var _this= this;
    		var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#dataForm").valid()){
				return false;
			}
    		var userId = $("#dataForm [name=userId]").val();
    		var griwthValue = $("#dataForm [name=griwthValue]").val();
    		var type = $("#dataForm [name=type]:checked").val();
    		if(type!="0"){
    			griwthValue=-griwthValue;
    		}
    		var griwthResource = $("#dataForm [name=griwthResource]").val();
    		
    		$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/ycusergriwthvalue/insertGriwthValueInfo",
				dataType : "json",
				data : {
					userId:userId,
					griwthValue:griwthValue,
					griwthResource:griwthResource
				},
				message : "正在加载数据..",
				success : function(rs) {
					if(rs.statusCode==1){
						showSuccessDialog(rs.statusInfo);
					}else{
						var d = Dialog({
							title: '消息',
							content:"修改失败",
							icon:'prompt',
							okValue: '确 定',
							ok:function(){
								this.close();
							}
						});
						d.show();
					}
				}
			});
    	},
    	_detailPage:function(orderId){
			window.location.href = _base+"/user/toUserDetail?userId="
            + orderId+'&mod=edit'+'&isAll=all'+"&random="+Math.random();
		},
		
    });
    
    module.exports = UserListPager;
});

