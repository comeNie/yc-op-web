package com.ai.yc.op.web.model.user;

import java.io.Serializable;

public class ExAllUser implements Serializable{
	
	private String userId;
	/**
	 * 昵称
	 */
    private String nickname;
    /**
     * 姓
     */
    private String lastname;
    /**
     * 名
     */
    private String firstname;
    /**
     * 性别
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;

    private String telephone;
    /**
     * 电话
     */
    private String mobilePhone;

    private String fixPhone;

    private String qq;
    /**
     * 地址
     */
    private String address;
    /**
     * 城市
     */
    private String cnCity;
    /**
     * 省份
     */
    private String province;
    /**
     * 国家
     */
    private String country;
    /**
     * 时区
     */
    private String tZone;

    private String occupation;

    private String title;
    /**
     * 会员等级
     */
    private String safetyLevel;
    /**
     * 注册时间
     */
    private String registTime;
    /**
     * 修改时间
     */
    private String lastModifyTime;
    /**
     * 用户来源
     */
    private String usersource;

    private String state;

    private String personsign;

    private String zipCode;

    private String portraitId;

    private String isChange;
    /**
     * 是否是译员
     */
    private String isTranslator;
    /**
     * 账户id
     */
    private String accountId;

    private String griwthLevel;

    private String griwthValue;
	// 登录次数
	private String loginCount;
	// 余额
	private String balance;
	// 积分
	private String integral;
	/**
	 * 是否是企业
	 */
	private String isCompany;
	/**
	 * email
	 */
	private String email;
	/**
	 * 账户状态
	 */
	private String acctStatus;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 收藏数
	 */
	private String collectCount;
	/**
	 * 注册时IP
	 */
	private String registIP;
	
	private String translatorLevel;

	public String getUserId() {
		return userId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getSex() {
		return sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getFixPhone() {
		return fixPhone;
	}

	public String getQq() {
		return qq;
	}

	public String getAddress() {
		return address;
	}

	public String getCnCity() {
		return cnCity;
	}

	public String getProvince() {
		return province;
	}

	public String getCountry() {
		return country;
	}

	public String gettZone() {
		return tZone;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getTitle() {
		return title;
	}

	public String getSafetyLevel() {
		return safetyLevel;
	}

	public String getRegistTime() {
		return registTime;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public String getUsersource() {
		return usersource;
	}

	public String getState() {
		return state;
	}

	public String getPersonsign() {
		return personsign;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getPortraitId() {
		return portraitId;
	}

	public String getIsChange() {
		return isChange;
	}

	public String getIsTranslator() {
		return isTranslator;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getGriwthLevel() {
		return griwthLevel;
	}

	public String getGriwthValue() {
		return griwthValue;
	}

	public String getLoginCount() {
		return loginCount;
	}

	public String getBalance() {
		return balance;
	}

	public String getIntegral() {
		return integral;
	}

	public String getIsCompany() {
		return isCompany;
	}

	public String getEmail() {
		return email;
	}

	public String getAcctStatus() {
		return acctStatus;
	}

	public String getUsername() {
		return username;
	}

	public String getCollectCount() {
		return collectCount;
	}

	public String getRegistIP() {
		return registIP;
	}

	public String getTranslatorLevel() {
		return translatorLevel;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setFixPhone(String fixPhone) {
		this.fixPhone = fixPhone;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCnCity(String cnCity) {
		this.cnCity = cnCity;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void settZone(String tZone) {
		this.tZone = tZone;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSafetyLevel(String safetyLevel) {
		this.safetyLevel = safetyLevel;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public void setUsersource(String usersource) {
		this.usersource = usersource;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPersonsign(String personsign) {
		this.personsign = personsign;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setPortraitId(String portraitId) {
		this.portraitId = portraitId;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	public void setIsTranslator(String isTranslator) {
		this.isTranslator = isTranslator;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setGriwthLevel(String griwthLevel) {
		this.griwthLevel = griwthLevel;
	}

	public void setGriwthValue(String griwthValue) {
		this.griwthValue = griwthValue;
	}

	public void setLoginCount(String loginCount) {
		this.loginCount = loginCount;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public void setIsCompany(String isCompany) {
		this.isCompany = isCompany;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCollectCount(String collectCount) {
		this.collectCount = collectCount;
	}

	public void setRegistIP(String registIP) {
		this.registIP = registIP;
	}

	public void setTranslatorLevel(String translatorLevel) {
		this.translatorLevel = translatorLevel;
	}

}
