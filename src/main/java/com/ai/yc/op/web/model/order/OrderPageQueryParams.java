package com.ai.yc.op.web.model.order;

import com.ai.yc.order.api.orderquery.param.QueryOrderRequest;

public class OrderPageQueryParams extends QueryOrderRequest {

	private static final long serialVersionUID = 1L;

	
	private Long orderTimeS;
	private Long orderTimeE;
	private Long payTimeS;
	private Long payTimeE;
	private String orderPageId;
	private String langungePaire;

	private Long cancelTimeS;
	private Long cancelTimeE;
	private String cancelType;

	private Long updateTimeS;
	private Long updateTimeE;

	private Long lockTimeS;
	private Long lockTimeE;

	private Long submitTimeS;
	private Long submitTimeE;
	
	private Long evaluateTimeS;
	private Long evaluateTimeE;
	public Long getOrderTimeS() {
		return orderTimeS;
	}
	public void setOrderTimeS(Long orderTimeS) {
		this.orderTimeS = orderTimeS;
	}
	public Long getOrderTimeE() {
		return orderTimeE;
	}
	public void setOrderTimeE(Long orderTimeE) {
		this.orderTimeE = orderTimeE;
	}
	public Long getPayTimeS() {
		return payTimeS;
	}
	public void setPayTimeS(Long payTimeS) {
		this.payTimeS = payTimeS;
	}
	public Long getPayTimeE() {
		return payTimeE;
	}
	public void setPayTimeE(Long payTimeE) {
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
	public Long getCancelTimeS() {
		return cancelTimeS;
	}
	public void setCancelTimeS(Long cancelTimeS) {
		this.cancelTimeS = cancelTimeS;
	}
	public Long getCancelTimeE() {
		return cancelTimeE;
	}
	public void setCancelTimeE(Long cancelTimeE) {
		this.cancelTimeE = cancelTimeE;
	}
	public String getCancelType() {
		return cancelType;
	}
	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}
	public Long getUpdateTimeS() {
		return updateTimeS;
	}
	public void setUpdateTimeS(Long updateTimeS) {
		this.updateTimeS = updateTimeS;
	}
	public Long getUpdateTimeE() {
		return updateTimeE;
	}
	public void setUpdateTimeE(Long updateTimeE) {
		this.updateTimeE = updateTimeE;
	}
	public Long getLockTimeS() {
		return lockTimeS;
	}
	public void setLockTimeS(Long lockTimeS) {
		this.lockTimeS = lockTimeS;
	}
	public Long getLockTimeE() {
		return lockTimeE;
	}
	public void setLockTimeE(Long lockTimeE) {
		this.lockTimeE = lockTimeE;
	}
	public Long getSubmitTimeS() {
		return submitTimeS;
	}
	public void setSubmitTimeS(Long submitTimeS) {
		this.submitTimeS = submitTimeS;
	}
	public Long getSubmitTimeE() {
		return submitTimeE;
	}
	public void setSubmitTimeE(Long submitTimeE) {
		this.submitTimeE = submitTimeE;
	}
	public Long getEvaluateTimeS() {
		return evaluateTimeS;
	}
	public void setEvaluateTimeS(Long evaluateTimeS) {
		this.evaluateTimeS = evaluateTimeS;
	}
	public Long getEvaluateTimeE() {
		return evaluateTimeE;
	}
	public void setEvaluateTimeE(Long evaluateTimeE) {
		this.evaluateTimeE = evaluateTimeE;
	}

	

}
