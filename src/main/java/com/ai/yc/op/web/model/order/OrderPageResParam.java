package com.ai.yc.op.web.model.order;

import java.util.List;

import com.ai.yc.order.api.orderquery.param.OrdOrderVo;

public class OrderPageResParam extends OrdOrderVo {
	private static final long serialVersionUID = 1L;
	private String chlIdPage;
	private String orderTypePage;
	private String payStylePage;
	private String statePage;
	private String totalFeePage;
	private String cancelTypePage;

	private String orderLevelPage;
	
	private List<OrdTransLevelVo> ordTransLevelList;
	
	private int levelSize;
	
	private int extendSize;
	

	public String getChlIdPage() {
		return chlIdPage;
	}

	public void setChlIdPage(String chlIdPage) {
		this.chlIdPage = chlIdPage;
	}

	public String getOrderTypePage() {
		return orderTypePage;
	}

	public void setOrderTypePage(String orderTypePage) {
		this.orderTypePage = orderTypePage;
	}

	public String getPayStylePage() {
		return payStylePage;
	}

	public void setPayStylePage(String payStylePage) {
		this.payStylePage = payStylePage;
	}

	public String getStatePage() {
		return statePage;
	}

	public void setStatePage(String statePage) {
		this.statePage = statePage;
	}

	public String getTotalFeePage() {
		return totalFeePage;
	}

	public void setTotalFeePage(String totalFeePage) {
		this.totalFeePage = totalFeePage;
	}

	public String getCancelTypePage() {
		return cancelTypePage;
	}

	public void setCancelTypePage(String cancelTypePage) {
		this.cancelTypePage = cancelTypePage;
	}

	public String getOrderLevelPage() {
		return orderLevelPage;
	}

	public void setOrderLevelPage(String orderLevelPage) {
		this.orderLevelPage = orderLevelPage;
	}

	public List<OrdTransLevelVo> getOrdTransLevelList() {
		return ordTransLevelList;
	}

	public void setOrdTransLevelList(List<OrdTransLevelVo> ordTransLevelList) {
		this.ordTransLevelList = ordTransLevelList;
	}

	public int getLevelSize() {
		return levelSize;
	}

	public void setLevelSize(int levelSize) {
		this.levelSize = levelSize;
	}

	public int getExtendSize() {
		return extendSize;
	}

	public void setExtendSize(int extendSize) {
		this.extendSize = extendSize;
	}


}
