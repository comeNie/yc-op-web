package com.ai.yc.op.web.model.order;

import java.sql.Timestamp;

public class ExAllOrder {
	private String chlId;
	private String orderType;
	private Long orderId;
	private String orderTime;
	
	private Timestamp time;
	private String userName;
	private String langire;
	private String totalFee;
	private String realFee;
	private String payStyle;
	private String payTime;
	private String state;
	
	private String cancelType;
	private String cancelTime;
	
	private String updateTime;
	private String updateName;
	private String orderLevel;
	
	private String lockTime;
	private String remaningTime;
	private String finishTime;
	
	private String submitTime;
	
	private String translateLevel;
	
	
	private String interperName;
	
	private String endChgTime;
	
	
	
	public String getInterperName() {
		return interperName;
	}

	public void setInterperName(String interperName) {
		this.interperName = interperName;
	}

	public String getChlId() {
		return chlId;
	}

	public void setChlId(String chlId) {
		this.chlId = chlId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLangire() {
		return langire;
	}

	public void setLangire(String langire) {
		this.langire = langire;
	}

	public String getPayStyle() {
		return payStyle;
	}

	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getRealFee() {
		return realFee;
	}

	public void setRealFee(String realFee) {
		this.realFee = realFee;
	}

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(String orderLevel) {
		this.orderLevel = orderLevel;
	}

	public String getLockTime() {
		return lockTime;
	}

	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}

	public String getRemaningTime() {
		return remaningTime;
	}

	public void setRemaningTime(String remaningTime) {
		this.remaningTime = remaningTime;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getTranslateLevel() {
		return translateLevel;
	}

	public void setTranslateLevel(String translateLevel) {
		this.translateLevel = translateLevel;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getEndChgTime() {
		return endChgTime;
	}

	public void setEndChgTime(String endChgTime) {
		this.endChgTime = endChgTime;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	

}
