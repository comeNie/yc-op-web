package com.ai.yc.op.web.model.order;

import com.ai.yc.order.api.orderdetails.param.QueryOrderDetailsResponse;

public class OrdOrderDetails extends QueryOrderDetailsResponse{
	
	private static final long serialVersionUID = -623256768762168693L;

	/**
     * 用户名
     */
    private String username;
    
    /**
     * 用户昵称
     */
    private String usernick;
    
    /**
     * 译员
     */
    private String interperName;
    
    /**
     * lsp
     */
    private String lspName;
    /**
     * 译员级别
     */
    private String interperLevel;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernick() {
		return usernick;
	}

	public void setUsernick(String usernick) {
		this.usernick = usernick;
	}

	public String getInterperName() {
		return interperName;
	}

	public void setInterperName(String interperName) {
		this.interperName = interperName;
	}

	public String getLspName() {
		return lspName;
	}

	public void setLspName(String lspName) {
		this.lspName = lspName;
	}

	public String getInterperLevel() {
		return interperLevel;
	}

	public void setInterperLevel(String interperLevel) {
		this.interperLevel = interperLevel;
	}
    
    

}
