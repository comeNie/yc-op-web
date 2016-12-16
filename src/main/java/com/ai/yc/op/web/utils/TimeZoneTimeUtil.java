package com.ai.yc.op.web.utils;

import java.sql.Timestamp;

import com.ai.paas.ipaas.i18n.ZoneContextHolder;

public class TimeZoneTimeUtil {
	public static String getTimes(Timestamp timeReq){
		String zone = ZoneContextHolder.getZone();
		int time = 0;
		if(zone.contains("+")){
			time=Integer.valueOf(zone.replace("GMT+", ""));
		}else if(zone.contains("-")){
			time=Integer.valueOf(zone.replace("GMT+", "-"));
		}
		return  DateCycleUtil.getTimestamp(timeReq,"H",time).toString();
	}

}
