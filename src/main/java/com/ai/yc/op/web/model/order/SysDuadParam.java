package com.ai.yc.op.web.model.order;

import com.ai.yc.common.api.sysduad.param.SysDuadVo;

public class SysDuadParam extends SysDuadVo {
	private static final long serialVersionUID = 1L;
	private String languaNmae;
	private String transTypeName;

	public String getLanguaNmae() {
		return languaNmae;
	}

	public void setLanguaNmae(String languaNmae) {
		this.languaNmae = languaNmae;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

}
