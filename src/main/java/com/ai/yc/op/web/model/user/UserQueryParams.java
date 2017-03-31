package com.ai.yc.op.web.model.user;

import java.io.Serializable;
import java.util.Date;

import com.ai.opt.base.vo.BaseInfo;

public class UserQueryParams implements Serializable{

	private String nickname ;
	
	private String mobilePhone;
	
	private String usersource;
	
	private String zhuce;
	
	private String safetyLevel;
	
	private Integer state;
	
	private Integer isTranslator;
	//是否是企业用户
	private Integer isCompany;
	
	private Long registTimeStart;
	
	private Long registTimeEnd;
	
	private Long lastLoginTimeStart;
	
	private Long lastLoginTimeEnd;
	
	private Integer pageSize;
	
	private Integer pageNo;

	public String getNickname() {
		return nickname;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getUsersource() {
		return usersource;
	}

	public String getZhuce() {
		return zhuce;
	}

	public String getSafetyLevel() {
		return safetyLevel;
	}

	public Integer getState() {
		return state;
	}

	public Integer getIsTranslator() {
		return isTranslator;
	}

	public Integer getIsCompany() {
		return isCompany;
	}

	public Long getRegistTimeStart() {
		return registTimeStart;
	}

	public Long getRegistTimeEnd() {
		return registTimeEnd;
	}

	public Long getLastLoginTimeStart() {
		return lastLoginTimeStart;
	}

	public Long getLastLoginTimeEnd() {
		return lastLoginTimeEnd;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setUsersource(String usersource) {
		this.usersource = usersource;
	}

	public void setZhuce(String zhuce) {
		this.zhuce = zhuce;
	}

	public void setSafetyLevel(String safetyLevel) {
		this.safetyLevel = safetyLevel;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setIsTranslator(Integer isTranslator) {
		this.isTranslator = isTranslator;
	}

	public void setIsCompany(Integer isCompany) {
		this.isCompany = isCompany;
	}

	public void setRegistTimeStart(Long registTimeStart) {
		this.registTimeStart = registTimeStart;
	}

	public void setRegistTimeEnd(Long registTimeEnd) {
		this.registTimeEnd = registTimeEnd;
	}

	public void setLastLoginTimeStart(Long lastLoginTimeStart) {
		this.lastLoginTimeStart = lastLoginTimeStart;
	}

	public void setLastLoginTimeEnd(Long lastLoginTimeEnd) {
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}




}
