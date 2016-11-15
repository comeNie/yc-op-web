package com.ai.yc.op.web.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 订单编码
 * @author hougang@asiainfo.com
 * @date 2016年11月8日 下午3:27:01 
 * @version V1.0
 */
public class OrderCodeUtils {
	
	/**
	 * 订单状态(后厂)
	 */
	private static Map<String,String> stateMap = new HashMap<String,String> ();
	
	static{
		initStateMap();
	}
	
	
	public static String getStateName(String code){
		return stateMap.get(code);
	}
	private static void initStateMap(){
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
	}

}
