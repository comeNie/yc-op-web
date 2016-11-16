package com.ai.yc.op.web.model.order;

import com.ai.yc.order.api.orderquery.param.OrdProdLevelVo;

public class OrdTransLevelVo extends OrdProdLevelVo{

	private static final long serialVersionUID = 1L;
	/**
	 * 翻译级别
	 */
	private String translateLevelPage;
	public String getTranslateLevelPage() {
		return translateLevelPage;
	}
	public void setTranslateLevelPage(String translateLevelPage) {
		this.translateLevelPage = translateLevelPage;
	}
	

}
