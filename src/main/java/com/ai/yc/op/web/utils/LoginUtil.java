package com.ai.yc.op.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;

public final class LoginUtil {
	private LoginUtil(){}
	
	public static GeneralSSOClientUser getLoginUser() {
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

	    GeneralSSOClientUser loginUser = (GeneralSSOClientUser) request.getSession().getAttribute(
	    		SSOClientConstants.USER_SESSION_KEY);
        return loginUser;
	}
}
