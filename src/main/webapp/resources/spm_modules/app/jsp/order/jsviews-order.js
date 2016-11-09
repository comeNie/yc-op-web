define(function (require, exports, module) {

	require("jsviews/jsrender");
	require("jsviews/jsviews");
	
	
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
	
	//翻译类型
	var translateTypeMap = new jMap();
	translateTypeMap.put("0", "文本翻译");
	translateTypeMap.put("1", "文档翻译");
	translateTypeMap.put("2", "口译翻译");
	
	//支付方式
	var payStyleMap = new jMap();
	payStyleMap.put("0", "余额");
	payStyleMap.put("1", "支付宝");
	payStyleMap.put("2", "网银");
	payStyleMap.put("3", "pay pal");
	payStyleMap.put("5", "后付");
	payStyleMap.put("6", "积分");
	payStyleMap.put("7", "优惠券");
	payStyleMap.put("11", "银行汇款/转账");
	
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
