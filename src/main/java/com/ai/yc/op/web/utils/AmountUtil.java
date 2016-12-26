package com.ai.yc.op.web.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.ai.opt.sdk.util.StringUtil;

public class AmountUtil {
	private AmountUtil() {
		// TODO Auto-generated constructor stub
	}
    
    /** 
     * 将厘为单位的转换为元 （除1000）  
     * 
     */
    public static String liToYuan(Long amount){
        if(amount == null || amount == 0){
            return "0.00";
        }
        BigDecimal balance = BigDecimal.valueOf(amount).divide(new BigDecimal(1000L),2,BigDecimal.ROUND_HALF_UP);
        return new DecimalFormat(",###,##0.00").format(balance);
    }
    /** 
     * 将元为单位的转换为里 （乘1000）  
     * 
     */
    public static Long yuanToLi(Long amount){
        if(amount == null || amount == 0){
            return 0L;
        }
        BigDecimal balance = BigDecimal.valueOf(amount).multiply(new BigDecimal(1000L));
        return balance.longValue();
    }
    
    /** 
     * 将元为单位的转换为里 （乘1000）  
     * 
     */
    public static Long yToLi(String amount){
        if(StringUtil.isBlank(amount)){
            return 0L;
        }
        BigDecimal money=new BigDecimal(amount);
        BigDecimal balance = money.multiply(new BigDecimal(1000L));
        return balance.longValue();
    }
    /** 
     * 将元为单位的转换为分 （乘100）  
     * 
     */
    public static Long yToFen(String amount){
        if(StringUtil.isBlank(amount)){
            return 0L;
        }
        BigDecimal money=new BigDecimal(amount);
        BigDecimal balance = money.multiply(new BigDecimal(100L));
        return balance.longValue();
    }
    
    /** 
     * 将元为单位的转换为分 （乘100）  
     * 
     */
    public static String yToSFen(String amount){
    	if(StringUtil.isBlank(amount)){
    		return "0";
    	}
    	 BigDecimal money=new BigDecimal(amount);
    	 BigDecimal balance = money.multiply(new BigDecimal(100L));
         return new DecimalFormat(",###,##0").format(balance);
    }
    /** 
     * 将分为单位的转换为里 （乘10）  
     * 
     */
    public static Long fToL(String amount){
    	if(StringUtil.isBlank(amount)){
    		return 0L;
    	}
    	 BigDecimal money=new BigDecimal(amount);
    	 BigDecimal balance = money.multiply(new BigDecimal(10L));
    	 return balance.longValue();
    }
    
}
