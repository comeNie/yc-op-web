define('app/jsp/order/orderdetails', function(require, exports, module) {
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
    
	var orderDetailsPager = Widget.extend({

		attrs : {
			clickId:""
		},
		Statics : {
			
		},
		// 事件代理
		events : {
			"change #orderLevel":"_getInterperLevel",
			"change .price":"_getTotalPrice",
			"change #totalFee":"_totalFeeChange",
			"click #save":"_save",
			"click #cancel":"_cancel",
			"change #useCode":"_changeWordPrice",
			"change #translateLevel":"_changeWordPrice"
		},
		// 重写父类
		setup : function() {
			var _this = this;
			orderDetailsPager.superclass.setup.call(this);
			
			//日期控件
			$(document).on("click",".fa-calendar",function(){
				var calInput = $(this).parent().prev();
				var timeId = calInput.attr('id');
				WdatePicker({el:timeId,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});
			});
			
			//下载事件
			$(document).on("click",".download",function(){
				var parent = $(this).parent();
				var fileId = parent.find("input").eq(0).val();
				var fileName = parent.find("input").eq(1).val();
				var ext = fileName.substring(fileName.indexOf('.')+1);
				location.href = _base + '/attachment/download?fileId='+fileId+'&ext='+ext;
			});
			this._queryOrderDetails();
			
		},
		_save:function(){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#orderForm").valid()){
				return false;
			}
			var param = $("#orderForm").serializeArray();
			ajaxController.ajax({
				type: "post",
				processing: true,
				message: "保存数据中，请等待...",
				url: _base + "/order/updateOrderInfo",
				data: param,
				success: function (rs) {
					showSuccessDialog(rs.statusInfo);
				}
			});
		},
        _cancel:function(){
        	if(cache){
        		this._initView(cache);
        	}
		},
		_getInterperLevel:function(){
			var orderLevel = $("#orderLevel").val();
			if(orderLevel=='1'){
	        	$("#interperLevel").html("V1、V2、V3、V4级译员");
	        }else if(orderLevel=='2'){
	            $("#interperLevel").html("V2、V3、V4级译员");
	        }else if(orderLevel=='3'){
                $("#interperLevel").html("V3、V4级译员");
            }else if(orderLevel=='4'){
            	$("#interperLevel").html("V4级(lsp)译员");
            }
		},
		_getTotalPrice:function(){
			var wordPrice = $("#wordPrice").val();
			var translateSum = $("#translateSum").val();//字数
			var isSetType = $("#isSetType").val();//是否排版
			var setTypeFee = $("#setTypeFee").val();//排版费用
			var descTypeFee = $("#descTypeFee").val();//转换格式费用
			var isUrgent = $("#isUrgent").val();//是否加急
			var urGentFee = $("#urGentFee").val();//加急费用
			var totalFee = 0;
			if(translateSum!=''){
				totalFee = totalFee + (parseFloat(wordPrice)*parseInt(translateSum));
			}
			if(isSetType=='Y'){
				totalFee = totalFee + parseFloat(setTypeFee);
			}
			totalFee = totalFee + parseFloat(descTypeFee);
			if(isUrgent=='Y'){
				totalFee = totalFee + parseFloat(urGentFee);
			}
			if(totalFee){
				$("#totalFee").val(totalFee);
				this._getOrderLevel(totalFee);
			}
			
		},
		_totalFeeChange:function(){
			this._getOrderLevel(null);
		},
		_getOrderLevel:function(fee){
			var _this = this;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#orderForm").valid()){
				return false;
			}
			var translateType = $("#translateType").val();
			var totalFee = $("#totalFee").val();
			var translateLevel = $("#translateLevel").val();
			var isUrgent = $("#isUrgent").val();
			var param = {};
			if(fee){
				param.fee = fee;
			}else{
				param.fee = totalFee;
			}
			param.translateLevel = translateLevel;
			param.translateType = translateType;
			if(isUrgent){
				param.isUrgent = 'N';
			}else{
				param.isUrgent = 'Y';
			}
			ajaxController.ajax({
				type: "post",
				processing: false,
				message: "加载数据中，请等待...",
				url: _base + "/order/getOrderLevel",
				data: param,
				success: function (rs) {
					$("#orderLevel").val(rs.data);
				}
			});
			
		},
		_getWordPrice:function(useCode, duadId, translateLevel,changeTotalPrice){
			if(!useCode||!duadId){
				return;
			}
			var _this = this;
			var priceShow = $("#priceShow");
			var wordPrice = $("#wordPrice");
			if(priceShow&&wordPrice){
			  var param = {};
				param.wordNum = 1;
				param.duadId = duadId;
				param.purposeId = useCode;
				param.translateLevel = translateLevel;
				param.isUrgent = false;
				var unit = $("#currencyUnit").val();
				if('2'==unit){
					param.language = "us_EN"
				}else{
					param.language = "zh_CN"
				}
				ajaxController.ajax({
					type: "post",
					processing: false,
					message: "加载数据中，请等待...",
					url: _base + "/order/queryAutoOffer",
					data: param,
					success: function (rs) {
						var li = rs.data.price;	
						if(li>0){
							
							var yuan = _this.fmoney(parseInt(li)/1000, 2);
							
							wordPrice.val(yuan);
							var currencyUnit = "元";
							if(rs.data.currencyUnit=='2'){
								currencyUnit = "美元";
					        }
							priceShow.html( _this.fmoney(yuan*1000, 2)+currencyUnit+"/1000字"); 
							if(changeTotalPrice){
								_this._getTotalPrice();
							}
						}
					}
				});
			  
			
			}
			
		},
		fmoney:function(s, n) {
			n = n > 0 && n <= 20 ? n : 2;
			s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
			var l = s.split(".")[0].split("").reverse(),
		    r = s.split(".")[1];
			var t = "";
			for(var i = 0; i < l.length; i ++ ){   
				t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
			}
			return t.split("").reverse().join("") + "." + r;
		},
		_initUploaderBtn:function(){
			var _this = this;
			$(".upload").each(function(){
				_this._bindUploader($(this).attr("id"));
			});
		},
		_bindUploader:function(id){
			
			var uploader = WebUploader.create({
		    	  swf : _base+"/resources/spm_modules/webuploader/Uploader.swf",
		          server: _base + '/attachment/upload/one',
		          auto : true,
		          pick : {
		        	  id:"#"+id,
		        	  innerHTML:"",
		        	  multiple:false
		          },
		          accept: {
		      	    title: 'intoTypes',
		      	    extensions: 'rar,zip,doc,xls,docx,xlsx,pdf,txt',
		      	    mimeTypes: '.rar,.zip,.doc,.xls,.docx,.xlsx,.pdf,.txt'
		      		},
	             resize : false,
	             fileNumLimit:300,
	             fileSizeLimit: 200 * 1024 * 1024,// 200 M
	         });
			
			 uploader.on("fileQueued",function(file){
		          
		     });
			
			 uploader.on('uploadError', function(file) {
				 showErrorDialog("上传文件失败");
	         });
			  
			  uploader.on('uploadSuccess',function(file, resp) {
					var parent = $("#"+id).parent();
					var fileId = parent.find("input").eq(0);
					var fileName = parent.find("input").eq(1);
					var prev2 = parent.prev().prev();
					if(resp.statusCode=='1'){
						fileId.val(resp.data.fileId);
						fileName.val(resp.data.fileName);
						prev2.html(resp.data.fileName);
					}else{
						showErrorDialog(resp.statusInfo);
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
				url: _base + "/order/queryOrderDetails",
				data: param,
				success: function (rs) {
					cache = rs;
					_this._initView(rs);
				}
			    
			});
		},
		_changeWordPrice:function(){
			var useCode = $("#useCode").val();
			var translateLevel = $("#translateLevel").val();
			if(cache&&useCode&&translateLevel){
				this._getWordPrice(useCode,cache.data.prodExtends[0].langungePair,translateLevel,false);
			}
		},
		_initView:function(rs){
			var _this = this;
			var model = $("#mod").val();
			rs.data.mod = model;
			var prodFiles = rs.data.prodFiles;
			if(prodFiles){
				for(var i=0;i<prodFiles.length;i++){
					prodFiles[i].fileNum = prodFiles.length;
					prodFiles[i].fileSubmitTime =  rs.data.orderTime;
					prodFiles[i].translateFileSubmitTime = rs.data.prod.updateTime;
					prodFiles[i].state = rs.data.state;
					prodFiles[i].mod = model;
				}
				rs.data.prodFiles = prodFiles;
			}
			var orderInfoHtml = $("#orderInfoTempl").render(rs.data);
			$("#date1").html(orderInfoHtml);
			
			var orderStateChgHtml = $("#orderStateChgTempl").render(rs.data);
			$("#orderStateChgTable").html(orderStateChgHtml);
			
			//初始化用途 领域下拉选择框
			if (rs.data.displayFlag=='11'||rs.data.displayFlag=='13'){
				_this.initDomainSelect('fieldCode',rs.data.prod.useCode);
				_this.initPurposeSelect('useCode',rs.data.prod.fieldCode);
			}
			_this._getInterperLevel();
			
			//初始化口译类型checkBox
			var prodLevels = rs.data.prodLevels;
			for(var i=0;i<prodLevels.length;i++){
				var checkBox = $(".checkbox-"+prodLevels[i].translateLevel);
				if(checkBox){
					checkBox.attr("checked","checked");
				}
			}
			
			//初始化单价
			_this._getWordPrice(rs.data.prod.useCode,rs.data.prodExtends[0].langungePair,rs.data.prodLevels[0].translateLevel,false);
			
			_this._initUploaderBtn();
			
			var formValidator =_this._initValidate();
			$(":input").bind("focusout",function(){
				formValidator.element(this);
			});
		},
		initDomainSelect:function(id,defaultVal){
			var select = $("#"+id);
			var param = {};
			ajaxController.ajax({
				type: "post",
				processing: false,
				message: "加载数据中，请等待...",
				url: _base + "/sys/querySysDomainList",
				data: param,
				success: function (rs) {
					var list = rs.data;
					var options = "";
					for(var i=0;i<list.length;i++){
						if(defaultVal&&list[i].domainId==defaultVal){
							options = options + "<option value='"+list[i].domainId+"' selected='selected'>"+list[i].domainCn+"</option>";
						}else{
							options = options + "<option value='"+list[i].domainId+"'>"+list[i].domainCn+"</option>";
						}
					}
					select.append(options);
				}
			});
		},
		initPurposeSelect:function(id,defaultVal){
			var select = $("#"+id);
			var param = {};
			ajaxController.ajax({
				type: "post",
				processing: false,
				message: "加载数据中，请等待...",
				url: _base + "/sys/querySysPurposeList",
				data: param,
				success: function (rs) {
					var list = rs.data;
					var options = "";
					for(var i=0;i<list.length;i++){
						if(defaultVal&&list[i].purposeId==defaultVal){
							options = options + "<option value='"+list[i].purposeId+"' selected='selected'>"+list[i].purposeCn+"</option>";
						}else{
							options = options + "<option value='"+list[i].purposeId+"'>"+list[i].purposeCn+"</option>";
						}
					}
					select.append(options);
				}
			});
		},
       _initValidate:function(){
    	   var formValidator = $("#orderForm").validate({
    			rules: {
    				"contacts.contactName":{
    					required:true
    				},
    				"contacts.contactTel": {
    					required:true,
    					regexp:/(^(86){0,1}1\d{10}$)|(^(00){0,1}(1){1}\d{10,12}$)/
    				},
    				"contacts.contactEmail":{
    					required:true,
    					email:true
    				},
    				"translateLevel":{
    					required:true
    				},
    				"prod.useCode":{
    					required:true
    				},
    				"prod.fieldCode":{
    					required:true
    				},
    				"prod.translateSum":{
    					digits:true
    				},
    				"setTypeFee":{
    					required:true,
    					number:true,
    					min:0
    				},
    				"descTypeFee":{
    					required:true,
    					number:true,
    					min:0
    				},
    				"urgentFee":{
    					required:true,
    					number:true,
    					min:0
    				},
    				"totalFee":{
    					required:true,
    					number:true,
    					min:0
    				},
    				"prod.meetingAddress":{
    					required:true
    				},
    				"prod.meetingSum":{
    					required:true,
    					digits:true,
    					max:100
    				},
    				"prod.interperSum":{
    					required:true,
    					digits:true,
    					max:100
    				},
    				"startTime":{
    					required:true,
    					minlength:8
    				},
    				"endTime":{
    					required:true,
    					minlength:8
    				},
    				"prod.takeDay":{
    					required:true,
    					digits:true
    				},
    				"prod.takeTime":{
    					required:true,
    					digits:true,
    					min:0,
    					max:24
    				}
    			},
    			messages: {
    				"contacts.contactName":{
    					required:"请输入联系人姓名"
    				},
    				"contacts.contactTel": {
    					required:"请输入联系人手机号",
    					regexp:"手机号格式不正确"
    				},
    				"contacts.contactEmail":{
    					required:"请输入联系人邮箱",
    					email:"邮箱格式不正确"
    				},
    				"translateLevel":{
    					required:"请选择口译类型"
    				},
    				"prod.useCode":{
    					required:"请选择翻译用途"
    				},
    				"prod.fieldCode":{
    					required:"请选择翻译领域"
    				},
    				"prod.translateSum":{
    					digits:"订单字数必须为整数"
    				},
    				"setTypeFee":{
    					required:"请输入排版费用",
    					number:"费用格式不正确",
    					min:"费用不合法"
    				},
    				"descTypeFee":{
    					required:"请输入格式转换费用",
    					number:"费用格式不正确",
    					min:"费用不合法"
    				},
    				"urgentFee":{
    					required:"请输入加急费用",
    					number:"费用格式不正确",
    					min:"费用不合法"
    				},
    				"totalFee":{
    					required:"请输入加急费用",
    					number:"费用格式不正确",
    					min:"费用不合法"
    				},
    				"prod.meetingAddress":{
    					required:"请输入会议地址"
    				},
    				"prod.meetingSum":{
    					required:"请输入会场数量",
    					digits:"会场数量必须为整数",
    					max:"会场数量不能超过100"
    				},
    				"prod.interperSum":{
    					required:"请输入译员数量",
    					digits:"译员数量必须为整数",
    					max:"译员数量不能超过100"
    				},
    				"startTime":{
    					required:"请选择开始时间",
    					minlength:"请选择开始时间"
    				},
    				"endTime":{
    					required:"请选择结束时间",
    					minlength:"请选择结束时间"
    				},
    				"prod.takeDay":{
    					required:"请输入翻译耗时天数",
    					digits:"翻译耗时天数必须为整数"
    				},
    				"prod.takeTime":{
    					required:"请输入翻译耗时小时数",
    					digits:"翻译耗时小时数必须为整数",
    				    min:"小时数不合法",
        				max:"小时数不合法"
    				}
    				
    			}
    		});
    		
    	   return formValidator ;

    	}
	});
	module.exports = orderDetailsPager;
});