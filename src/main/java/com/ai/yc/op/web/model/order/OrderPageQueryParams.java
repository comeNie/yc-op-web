package com.ai.yc.op.web.model.order;

import com.ai.yc.order.api.orderquery.param.QueryOrderRequest;

public class OrderPageQueryParams extends QueryOrderRequest {

	private static final long serialVersionUID = 1L;

	private String orderTimeS;
	private Long start;
	private Long end;
	private String orderTimeE;
	private String payTimeS;
	private String payTimeE;
	private String orderPageId;
	private String langungePaire;

	private String cancelTimeS;
	private String cancelTimeE;
	private String cancelType;

	private String updateTimeS;
	private String updateTimeE;

	private String lockTimeS;
	private String lockTimeE;

	private String submitTimeS;
	private String submitTimeE;

	public String getOrderTimeS() {
		return orderTimeS;
	}

	public void setOrderTimeS(String orderTimeS) {
		this.orderTimeS = orderTimeS;
	}

	public String getOrderTimeE() {
		return orderTimeE;
	}

	public void setOrderTimeE(String orderTimeE) {
		this.orderTimeE = orderTimeE;
	}

	public String getPayTimeS() {
		return payTimeS;
	}

	public void setPayTimeS(String payTimeS) {
		this.payTimeS = payTimeS;
	}

	public String getPayTimeE() {
		return payTimeE;
	}

	public void setPayTimeE(String payTimeE) {
		this.payTimeE = payTimeE;
	}

	public String getOrderPageId() {
		return orderPageId;
	}

	public void setOrderPageId(String orderPageId) {
		this.orderPageId = orderPageId;
	}

	public String getLangungePaire() {
		return langungePaire;
	}

	public void setLangungePaire(String langungePaire) {
		this.langungePaire = langungePaire;
	}

	public String getCancelTimeS() {
		return cancelTimeS;
	}

	public void setCancelTimeS(String cancelTimeS) {
		this.cancelTimeS = cancelTimeS;
	}

	public String getCancelTimeE() {
		return cancelTimeE;
	}

	public void setCancelTimeE(String cancelTimeE) {
		this.cancelTimeE = cancelTimeE;
	}

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public String getUpdateTimeS() {
		return updateTimeS;
	}

	public void setUpdateTimeS(String updateTimeS) {
		this.updateTimeS = updateTimeS;
	}

	public String getUpdateTimeE() {
		return updateTimeE;
	}

	public void setUpdateTimeE(String updateTimeE) {
		this.updateTimeE = updateTimeE;
	}

	public String getLockTimeS() {
		return lockTimeS;
	}

	public void setLockTimeS(String lockTimeS) {
		this.lockTimeS = lockTimeS;
	}

	public String getLockTimeE() {
		return lockTimeE;
	}

	public void setLockTimeE(String lockTimeE) {
		this.lockTimeE = lockTimeE;
	}

	public String getSubmitTimeS() {
		return submitTimeS;
	}

	public void setSubmitTimeS(String submitTimeS) {
		this.submitTimeS = submitTimeS;
	}

	public String getSubmitTimeE() {
		return submitTimeE;
	}

	public void setSubmitTimeE(String submitTimeE) {
		this.submitTimeE = submitTimeE;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

}
