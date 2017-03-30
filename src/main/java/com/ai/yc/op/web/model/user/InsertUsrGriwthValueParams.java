package com.ai.yc.op.web.model.user;

import java.io.Serializable;

public class InsertUsrGriwthValueParams implements Serializable {
	private static final long serialVersionUID = -4765432232526940603L;
	/**
     * 用户Id
     */
    private String userId;
    /**
     * 成长值
     */
    private Integer griwthValue;
    /**
     * 成长来源
     */
    private String griwthResource;
    /**
     * 来源详情
     */
    private String resourceDetail;
	public String getUserId() {
		return userId;
	}
	public Integer getGriwthValue() {
		return griwthValue;
	}
	public String getGriwthResource() {
		return griwthResource;
	}
	public String getResourceDetail() {
		return resourceDetail;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setGriwthValue(Integer griwthValue) {
		this.griwthValue = griwthValue;
	}
	public void setGriwthResource(String griwthResource) {
		this.griwthResource = griwthResource;
	}
	public void setResourceDetail(String resourceDetail) {
		this.resourceDetail = resourceDetail;
	}
    
    
}
