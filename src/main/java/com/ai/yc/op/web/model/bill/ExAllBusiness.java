package com.ai.yc.op.web.model.bill;


public class ExAllBusiness {
	/**
	 * 交易号
	 */
	private String serialCode;
	/**
	 *时间
	 */
	private String payTime;
	/**
	 *类型1:充值；2:下单；3:提现；4: 退款
	 */
	private String optType;
	/**
	 * 收支标记  1:收入   0:支出
	 */
	private String incomeFlag;

	/**
	 * 昵称
	 */
	private String accountName;

	/**
	 * 交易金额
	 */
	private String totalAmount;

	/**
	 * 异动前余额,通过该字段计算账户余额    账户余额 = 异动前余额 + 交易金额
	 */
	private String balancePre;

	/**
	 * 帐户余额
	 */
	private String balanceAmount;
	/**
	 * 支付方式  例如支付宝、银联、光大银行、中译语通科技有限公司
	 */
	private String channel;

	/**
	 * 交易状态 0 交易未完成 1 交易成功  2 交易失败,
	 */
	private String payStatus;
	/**
	 *详细说明 例如：充值；翻译下单，订单号：312321；
	 */
	private String remark;
	/**
	 *币种：1 人民币, 2 美元
	 */
	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getIncomeFlag() {
		return incomeFlag;
	}

	public void setIncomeFlag(String incomeFlag) {
		this.incomeFlag = incomeFlag;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBalancePre() {
		return balancePre;
	}

	public void setBalancePre(String balancePre) {
		this.balancePre = balancePre;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
}
