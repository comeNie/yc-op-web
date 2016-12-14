package com.ai.yc.op.web.model.order;

public class OrderDetailPagerRequest {

	/**
	 * 总费用(必填)
	 */
	private String totalFee;

	/**
	 * 排版费用(必填)
	 */
	private String setTypeFee;

	/**
	 * 加急费用(必填)
	 */
	private String urgentFee;

	/**
	 * 转换格式费用(必填)
	 */
	private String descTypeFee;

	/**
	 * 翻译级别
	 */
	private String[] translateLevel;
	/**
	 * 币种
	 */
	private String currencytUnit;

	/**
	 * 内容附件
	 */
	private String[] fileSaveIds;

	private String[] fileNames;

	private String[] fileTranslateIds;

	private String[] fileTranslateNames;

	private Long startTime;

	private Long endTime;

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getSetTypeFee() {
		return setTypeFee;
	}

	public void setSetTypeFee(String setTypeFee) {
		this.setTypeFee = setTypeFee;
	}

	public String getUrgentFee() {
		return urgentFee;
	}

	public void setUrgentFee(String urgentFee) {
		this.urgentFee = urgentFee;
	}

	public String getDescTypeFee() {
		return descTypeFee;
	}

	public void setDescTypeFee(String descTypeFee) {
		this.descTypeFee = descTypeFee;
	}

	public String[] getTranslateLevel() {
		return translateLevel;
	}

	public void setTranslateLevel(String[] translateLevel) {
		this.translateLevel = translateLevel;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String[] getFileSaveIds() {
		return fileSaveIds;
	}

	public void setFileSaveIds(String[] fileSaveIds) {
		this.fileSaveIds = fileSaveIds;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public String[] getFileTranslateIds() {
		return fileTranslateIds;
	}

	public void setFileTranslateIds(String[] fileTranslateIds) {
		this.fileTranslateIds = fileTranslateIds;
	}

	public String[] getFileTranslateNames() {
		return fileTranslateNames;
	}

	public void setFileTranslateNames(String[] fileTranslateNames) {
		this.fileTranslateNames = fileTranslateNames;
	}

	public String getCurrencytUnit() {
		return currencytUnit;
	}

	public void setCurrencytUnit(String currencytUnit) {
		this.currencytUnit = currencytUnit;
	}

}
