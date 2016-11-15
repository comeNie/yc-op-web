define(function (require, exports, module) {

	require("jsviews/jsrender");
	require("jsviews/jsviews");
	var AjaxController = require('opt-ajax/1.0.0/index');
	var ajaxController = new AjaxController();
	
	//订单来源
	var chlIdMap = new jMap();
	chlIdMap.put("0", "PC-中文站");
	chlIdMap.put("1", "PC-英文站");
	chlIdMap.put("2", "百度");
	chlIdMap.put("3", "金山");
	chlIdMap.put("4", "找翻译");
	chlIdMap.put("5", "WAP-中文");
	chlIdMap.put("6", "WAP-英语");
	chlIdMap.put("7", "微信助手");
	
	//翻译级别类型
	var translateLevelMap = new jMap();
	translateLevelMap.put("100110", "陪同翻译");
	translateLevelMap.put("100120", "交替传译");
	translateLevelMap.put("100130", "同声翻译");
	translateLevelMap.put("100210", "标准级");
	translateLevelMap.put("100220", "专业级");
	translateLevelMap.put("100230", "出版级");
	
	//翻译类型
	var translateTypeMap = new jMap();
	translateTypeMap.put("0", "文本翻译");
	translateTypeMap.put("1", "文档翻译");
	translateTypeMap.put("2", "口译翻译");
	
	//订单级别
	var orderLevelMap = new jMap();
	orderLevelMap.put("1", "V1");
	orderLevelMap.put("2", "V2");
	orderLevelMap.put("3", "V3");
	orderLevelMap.put("4", "V4");

	//支付方式
	var payStyleMap = new jMap();
	payStyleMap.put("YE", "余额");
	payStyleMap.put("ZFB", "支付宝");
	payStyleMap.put("YL", "网银");
	payStyleMap.put("PP", "pay pal");
	payStyleMap.put("HF", "后付");
	payStyleMap.put("JF", "积分");
	payStyleMap.put("YHQ", "优惠券");
	payStyleMap.put("HK", "银行汇款/转账");
	
	//订单后厂状态
	var stateMap = new jMap();
	stateMap.put("10", "提交");
	stateMap.put("11", "待支付");
	stateMap.put("12", "已支付");
	stateMap.put("13", "待报价");
	stateMap.put("20", "待领取");
	stateMap.put("21", "已领取");
	stateMap.put("211", "已分配");
	stateMap.put("23", "翻译中");
	stateMap.put("24", "已提交翻译");
	stateMap.put("25", "修改中");
	stateMap.put("40", "待审核");
	stateMap.put("41", "已审核");
	stateMap.put("42", "审核不通过");
	stateMap.put("50", "待确认");
	stateMap.put("51", "已确认");
	stateMap.put("52", "待评价");
	stateMap.put("53", "已评价");
	stateMap.put("90", "完成");
	stateMap.put("91", "已取消");
	stateMap.put("92", "已退款");
	
	/*
	 * 获取订单操作日志操作名称
	 */
	$.views.helpers({
		"getOrderOperName": function (subFlag,orgState,newState){	
			if(orgState==newState){
	        	return "修改订单";
	        }
			//系统自动报价
			if(subFlag==0){
				if(!newState||newState=='10'||newState=='11'){
					return "提交订单";
				}
			}else{
				if(!newState||newState=='10'||newState=='13'){
					return "提交订单";
				}
				if(newState=='11'){
					return "手动报价";
				}
			}
	        if(newState=='20'){
	        	return "支付订单";
	        }
	        if(newState=='21'){
	        	return "领取订单";
	        }
	        if(newState=='211'){
	        	return "分配订单";
	        }
	        return newState;
		 }
	});
	
	
	$.views.helpers({
		"getMoneyUnit": function (currencyUnit){	
			if(currencyUnit=='2'){
	        	return "美元";
	        }else{
	        	return "元";
	        }
		 }
	});
	
	//获取剩余时间
	$.views.helpers({
		"getOverplusTimes": function (stateChgTime,day){	
			var now  = new Date().getTime();
			
			var overplus = stateChgTime + day*24*60*60*1000 - now;
			alert(overplus);
			var d = parseInt(overplus/(24*60*60*1000));
			overplus = overplus%(24*60*60*1000);
			var h = Math.round(overplus/(60*60*1000));
			return d+"天"+h+"小时";
		 }
	});
	
	$.views.helpers({
		"getOrderLevelName": function (orderLevel){
			return orderLevelMap.get(orderLevel);
		},
		"getInterperLevel": function (orderLevel){	
			if(orderLevel=='1'){
	        	return "V1、V2、V3、V4级译员";
	        }else if(orderLevel=='2'){
	        	return "V2、V3、V4级译员";
	        }else if(orderLevel=='3'){
	            return "V3、V4级译员";
            }else if(orderLevel=='4'){
	            return "V4级(lsp)译员";
            }
            return "";
		 }
	});
	
	/**
	 * 获取订单后厂状态名称
	 */
	$.views.helpers({
		"getStateName": function (stateCode){	
	        return stateMap.get(stateCode);
		 },
		 "getChlIdName": function (chlId){	
		        return chlIdMap.get(chlId);
	     },
	     "getTranslateTypeName": function (translateType){	
		      return translateTypeMap.get(translateType);
	     },
	     "getPayStyleName": function (payStyle){	
		      return translateTypeMap.get(payStyle);
	     },
	     "getTranslateLevelName": function (translateLevel){	
		      return translateLevelMap.get(translateLevel);
	     },
	     "getPayStyleName": function (payStyle){	
		      return payStyleMap.get(payStyle);
	     }
	     
	});
	
	
	
	function jMap(){
        //私有变量
        var arr = {};
        //增加
        this.put = function(key,value){
            arr[key] = value;
        }
        //查询
        this.get = function(key){
            if(arr[key]){
                return arr[key]
            }else{
                return null;
            }
        }
        //删除
        this.remove = function(key){
            //delete 是javascript中关键字 作用是删除类中的一些属性
            delete arr[key]
        }
        //遍历
        this.eachMap = function(fn){
            for(var key in arr){
                fn(key,arr[key])
            }
        }
    }

});
