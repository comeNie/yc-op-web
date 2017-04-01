package com.ai.yc.op.web.model.user;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserUpdateParams implements Serializable{

	private static final long serialVersionUID = 376952768997469406L;
	
	private String userId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 手机
	 */
	private String mobilePhone;
	/**
	 * email
	 */
	@Email(message = "邮箱格式不正确")
	private String email;
	/**
	 * 用户状态
	 */
	private String state;

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getSex() {
		return sex;
	}

	public String getNickname() {
		return nickname;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
