package com.ai.yc.op.web.constant;



public final class Constants {
	private Constants() {

	}
	public static final String TENANT_ID = "yeecloud";
	/** 订单typeCode */
	public static final String TYPE_CODE = "ORD_ORDER";
	/** 订单状态paramCode */
	public static final String ORD_STATE = "STATE";
	/** 订单类型*/
	public static final String ORDER_TYPE = "ORDER_TYPE";
	/** 订单来源*/
	public static final String ORD_CHL_ID = "CHL_ID";
	/** 支付方式*/
	public static final String ORD_PAY_STYLE = "PAY_STYLE";
	/** 美元*/
	public static final String CURRENCY_UNIT_S = "2";
	/** 人民币*/
	public static final String CURRENCY_UNIT_R = "1";
	
	public static final class ExcelConstants{
    	private ExcelConstants(){}
    	
    	/**excel导出最大行数**/
    	public static final String EXCEL_OUTPUT_MAX_ROW = "/excel_output_max_row";
    }
}
